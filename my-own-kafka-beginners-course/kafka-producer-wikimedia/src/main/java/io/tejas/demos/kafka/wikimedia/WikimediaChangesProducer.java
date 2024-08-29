package io.tejas.demos.kafka.wikimedia;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class WikimediaChangesProducer {

    public static void main(String[] args) throws InterruptedException {
        String bootstrapServers = "127.0.0.1:9092";

        //if your producer isn't safe, it can lead to duplicate messages, loss of ordering etc.
        // create producer properties. kafka>3.0 has safe producers by default (acks=-1, max.inflight.requests=5, delivery.timeout.ms=120000, enable.idempotence=true) i.e idempotent producer. therefore, we don't need to worry about these things at all.
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        //properties for high throughput.
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024));
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

        //create the producer
        KafkaProducer<String,String> producer = new KafkaProducer<>(properties);

        String topic = "wikimedia.recentchange";

        //handle the events coming from the stream, and then send them to the producer. We create a class that implements the EventHandler interface.
        EventHandler eventHandler = new WikimediaChangeHandler(producer, topic);

        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));

        EventSource eventSource = builder.build();

        //Start reading from the eventSource. this starts in it's own thread.
        eventSource.start();

        //we want to produce for 10 minutes. therefore, we need to block the main thread for 10 minutes. otherwise, when the main thread ends, all the other threads will end as well and nothing will be read from the event source and produced to kafka.
        TimeUnit.MINUTES.sleep(10);

    }
}