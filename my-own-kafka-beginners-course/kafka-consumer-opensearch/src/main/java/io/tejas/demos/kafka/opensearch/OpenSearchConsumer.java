package io.tejas.demos.kafka.opensearch;

import com.google.gson.JsonParser;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.opensearch.action.bulk.BulkRequest;
import org.opensearch.action.bulk.BulkResponse;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.index.IndexResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.GetIndexRequest;
import org.opensearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class OpenSearchConsumer {

    private static RestHighLevelClient createOpenSearchClient() {
        String connString = "http://localhost:9200";

        // we build a URI from the connection string
        RestHighLevelClient restHighLevelClient;
        URI connUri = URI.create(connString);
        // extract login information if it exists
        String userInfo = connUri.getUserInfo();

        if (userInfo == null) {
            // REST client without security
            restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost(connUri.getHost(), connUri.getPort(), "http")));

        } else {
            // REST client with security
            String[] auth = userInfo.split(":");

            CredentialsProvider cp = new BasicCredentialsProvider();
            cp.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(auth[0], auth[1]));

            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(new HttpHost(connUri.getHost(), connUri.getPort(), connUri.getScheme()))
                            .setHttpClientConfigCallback(
                                    httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(cp)
                                            .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())));


        }

        return restHighLevelClient;
    }

    public static void main(String[] args) throws IOException {

        Logger log = LoggerFactory.getLogger(OpenSearchConsumer.class.getSimpleName());

        //create an opensearch client
        RestHighLevelClient openSearchClient = createOpenSearchClient();

        //create a kafka client
        KafkaConsumer<String, String> consumer = createKafkaConsumer();

        //get a reference to the main thread
        final Thread mainThread = Thread.currentThread();   //reference to the thread that is running my program.

        //add the shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run() {
                log.info("Detected a shutdown, let's exit by calling consumer.wakeup()...");
                consumer.wakeup();  //the next time the consumer polls for data (in the below while loop), the poll() method will throw a WakeupException, and this exception is what we have to catch in the while loop below. Therefore, we need a way to ensure that the shutdown hook now waits for the main program to finish(the main program should be given time to catch the exception, handle it, and gracefully close. it should not be killed before all these things are done.)

                //join the main thread to allow the execution of the code in the main thread
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        consumer.subscribe(Collections.singleton("wikimedia.recentchange"));

        //create the index on opensearch if it doesn't exist already.
        //Support for try-with-resources — introduced in Java 7 — allows us to declare resources to be used in a try block with the assurance that the resources will be closed after the execution of that block. The resources declared need to implement the AutoCloseable interface.
        //openSearchClient and consumer will be closed when this try block executes regardless of whether the try statement completes normally or abruptly.
        try(openSearchClient; consumer) {
            if (!openSearchClient.indices().exists(new GetIndexRequest("wikimedia"), RequestOptions.DEFAULT)) {
                CreateIndexRequest createIndexRequest = new CreateIndexRequest("wikimedia");
                openSearchClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
                log.info("The wikimedia index has been created");
            } else {
                log.info("The wikimedia index already exists");
            }

            while(true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(3000));

                int recordCount = records.count();
                log.info("Received " + recordCount + " record(s).");

                BulkRequest bulkRequest = new BulkRequest();

                //send the data into openSearch.
                for(ConsumerRecord<String, String> record : records) {
                    try {
                        //extract an ID from the message. This ID is unique. Then, we send this ID to opensearch. This is important because if we receive the same message twice (EG: Consumer processes some messages but crashes just before committing the offsets in AtLeastOnce mode, then it will re read the messages from the last committed offset when it comes back up, leading to a possible reprocessing of some messages), then pushing data with the same ID to opensearch will fail since it already exists in opensearch. this way, we remove duplicates from the data and have a clean data set.
                        String id = extractId(record.value());

                        IndexRequest indexRequest = new IndexRequest("wikimedia")
                                .source(record.value(), XContentType.JSON)
                                .id(id);

                        bulkRequest.add(indexRequest);  //add this request to the bulk.
                    } catch (Exception e) {
                    }
                }
                //push this bulkrequest to opensearch if the number of records is greater than 0.
                if(bulkRequest.numberOfActions() > 0) {
                    BulkResponse response = openSearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
                    log.info("Inserted " + response.getItems().length + " records.");

                    //no use of the below sleep. it's only to increase the chances of getting a bulk message from kafka (with more records), so that we process more records at once (500 at once, instead of maybe 100 5 times). This leads to higher efficiency since the number of calls to kafka and to opensearch are reduced.
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    consumer.commitSync();
                    log.info("Offsets have been committed!");
                }
            }
        } catch (WakeupException e) {
            log.info("Consumer is starting to shut down");
        } catch (Exception e) {
            log.error("Unexpected exception in the consumer", e);
        } finally {
            log.info("The consumer is now gracefully shut down");
        }
    }

    private static String extractId(String json) {
        return JsonParser.parseString(json)
                .getAsJsonObject()
                .get("meta")
                .getAsJsonObject()
                .get("id")
                .getAsString();
    }

    private static KafkaConsumer<String, String> createKafkaConsumer() {
        String groupId = "consumer-opensearch-demo";

        //create the producer properties and connect to the kafka broker.
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);    //defines the consumer group.

        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");   //has three values. none, earliest, latest. see the meaning of these values from the photos that you have taken in your phone. this value is picked up only if there are no committed offsets found for the partitions for this consumer group.
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        return new KafkaConsumer<>(properties);

    }
}
