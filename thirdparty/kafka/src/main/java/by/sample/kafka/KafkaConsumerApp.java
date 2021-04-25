package by.sample.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class KafkaConsumerApp {

    private static final String BOOTSTRAP_SERVERS = "bootstrap.servers";
    private static final String KEY_DESERIALIZER = "key.deserializer";
    private static final String VALUE_DESERIALIZER = "value.deserializer";
    private static final String MY_TOPIC = "my_topic";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(BOOTSTRAP_SERVERS, "localhost:9092");
        properties.put(KEY_DESERIALIZER, StringDeserializer.class.getCanonicalName());
        properties.put(VALUE_DESERIALIZER, StringDeserializer.class.getCanonicalName());

        List<String> topics = Collections.singletonList(MY_TOPIC);
        List<TopicPartition> topicPartitions = Collections.singletonList(new TopicPartition(MY_TOPIC, 0));
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            //consumer.subscribe(topics);
            consumer.assign(topicPartitions);
        }
    }
}
