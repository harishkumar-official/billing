package billing.service;

import billing.model.EventEntity;
import billing.model.TimeseriesDBEntity;
import billing.repository.TimeseriesDBRepository;
import billing.repository.UniquenessDBRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumerService {

  private Properties kafkaProperties;
  private TimeseriesDBRepository timeseriesDBRepository;
  private UniquenessDBRepository uniquenessDBRepository;
  private JsonMapper mapper = new JsonMapper();

  public KafkaConsumerService(@Value("kafka-properties") Properties kafkaProperties,
      TimeseriesDBRepository timeseriesDBRepository,
      UniquenessDBRepository uniquenessDBRepository) {
    this.kafkaProperties = kafkaProperties;
    this.timeseriesDBRepository = timeseriesDBRepository;
    this.uniquenessDBRepository = uniquenessDBRepository;
  }

  @PostConstruct
  public void start() throws JsonProcessingException {
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaProperties);
    consumer.subscribe(Arrays.asList("topic"));

    try {
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        // process records
        processRecords(records);
        // commit offset
        consumer.commitSync();
      }
    } finally {
      consumer.close();
    }
  }

  private void processRecords(ConsumerRecords<String, String> records)
      throws JsonProcessingException {

    List<EventEntity> toSave = new ArrayList<>();
    for (ConsumerRecord<String, String> record : records) {
      String data = record.value();
      EventEntity event;
      try {
        event = mapper.readValue(data, EventEntity.class);
      } catch (JsonProcessingException ex) {
        log.error("Json parse error, record:{}", record, ex);
        throw ex;
      }

      // check duplicates
      if (uniquenessDBRepository.exist(event.getUuid())) {
        continue;
      }
      toSave.add(event);
    }

    // save data in DB
    if (!toSave.isEmpty()) {
      uniquenessDBRepository.saveAll(toSave.stream()
          .map(EventEntity::getUuid).collect(Collectors.toList()));
      timeseriesDBRepository.saveAll(toSave.stream()
          .map(e -> mapper.convertValue(e, TimeseriesDBEntity.class)).collect(Collectors.toList()));
    }
  }
}
