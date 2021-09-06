package by.sample.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerApp {

    private static final String BOOTSTRAP_SERVERS = "bootstrap.servers";
    private static final String KEY_SERIALIZER = "key.serializer";
    private static final String VALUE_SERIALIZER = "value.serializer";

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.put(BOOTSTRAP_SERVERS, "localhost:9092");
        properties.put(KEY_SERIALIZER, StringSerializer.class.getCanonicalName());
        properties.put(VALUE_SERIALIZER, StringSerializer.class.getCanonicalName());

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            for (int i = 0; i < 100; i++) {
                ProducerRecord<String, String> record = new ProducerRecord<>("my_topic", Integer.toString(i), "Message " + i);
                producer.send(record);
            }
        }
    }
}
