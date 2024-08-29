package io.tejas.demos.kafka.wikimedia;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikimediaChangeHandler implements EventHandler {

    KafkaProducer<String,String> kafkaProducer;
    String topic;

    private final Logger log = LoggerFactory.getLogger(WikimediaChangeHandler.class.getSimpleName());

    //we created this constructor because after receiving a message from the eventSource, we need to produce it to our kafka. Therefore, we need a handle on the producer.
    public WikimediaChangeHandler(KafkaProducer<String,String> kafkaProducer, String topic) {
        this.kafkaProducer = kafkaProducer;
        this.topic = topic;
    }


    //EventSource calls this method when the stream connection has been opened.
    @Override
    public void onOpen() {
        //do nothing.
    }

    //EventSource calls this method when the stream connection has been closed.
    @Override
    public void onClosed() {
        kafkaProducer.close();
    }

    //EventSource calls this method when it has received a new event from the stream.
    @Override
    public void onMessage(String s, MessageEvent messageEvent) {
        log.info(messageEvent.getData());
        //asynchronous code
        kafkaProducer.send(new ProducerRecord<>(topic, messageEvent.getData()));
    }

    @Override
    public void onComment(String s) {
        //do nothing.
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error in reading the stream", throwable);
    }
}
