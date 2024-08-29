package io.tejas.demos.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());

    public static void main(String[] args) {
        log.info("Hello");

        //create the producer properties and connect to the kafka broker.
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        //create the producer
        KafkaProducer<String,String> producer = new KafkaProducer<>(properties);
        //create a Producer Record. It is a record that we are going to send to Kafka
        ProducerRecord<String,String> producerRecord = new ProducerRecord<>("demo_java", "hello world");

        //send data. this sends data aysnchronously.
        producer.send(producerRecord);

        //flush and close the producer
//        producer.flush();   //tell the producer to send all the data and block till done. synchronous operation. sending data "producer.send()" was async. not required, since producer.close() calls producer.flush() internally.

        producer.close();   //calls producer.flush() internally. very important, because our producer sends data async. If we don't call producer.close(), the process will exit and the producer will be deallocated before it's able to send the data to kafka.
        //so, by producer.close(), we wait for the kafka producer to push the data before exiting the process.
    }
}