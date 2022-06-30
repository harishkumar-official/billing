package billing.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import javax.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {

  private Properties kafkaProperties;

  public KafkaConsumerService(@Value("kafka-properties") Properties kafkaProperties) {
    this.kafkaProperties = kafkaProperties;
  }

  @PostConstruct
  public void start() {
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaProperties);
    consumer.subscribe(Arrays.asList("topic"));

    try {
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        // process records
        consumer.commitSync();
      }
    } finally {
      consumer.close();
    }
  }
}
