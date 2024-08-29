package io.tejas.demos.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemoWithShutdown {

    private static final Logger log = LoggerFactory.getLogger(ConsumerDemoWithShutdown.class.getSimpleName());

    public static void main(String[] args) {
        log.info("I am a Kafka consumer!");

        String groupId = "my-java-application";
        String topic = "demo_java";

        //create the producer properties and connect to the kafka broker.
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());

        properties.setProperty("group.id", groupId);    //defines the consumer group.

        properties.setProperty("auto.offset.reset", "earliest");   //has three values. none, earliest, latest. see the meaning of these values from the photos that you have taken in your phone. this value is picked up only if there are no committed offsets found for the partitions for this consumer group.

        //create a consumer.
        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(properties);

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

        try {
            //subscribe to a topic.
            consumer.subscribe(Arrays.asList(topic));

            //poll for data. infinite loop, does not have a proper shutdown hook.
            while (true) {
                ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));     //1000 is the timeout. our consumer will wait for one second to receive any data from kafka (any new data if present), before moving on with the rest of the code. Therefore, if any new data is present and received by our consumer before 1 second has passed, we will move on with the rest of the code without waiting for the 1 second to complete. Otherwise, we will wait for 1 second for a response from the kafka broker before moving on.

                for(ConsumerRecord<String,String> record : records) {
                    log.info("Key: " + record.key() + ", Value: " + record.value());
                    log.info("Partition: " + record.partition() + ", Offset: " + record.offset());
                }

            }
        } catch (WakeupException e) {
            log.info("Consumer is starting to shut down");
        } catch (Exception e) {
            log.error("Unexpected exception in the consumer", e);
        } finally {
            consumer.close();   //commits the offsets and closes the consumer. this also allows the consumer group to rebalance properly.
            log.info("The consumer is now gracefully shut down");
        }

    }
}