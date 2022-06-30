package billing.config;

import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class Resources {

  @Bean("kafka-properties")
  public Properties kafkaProperties() {
    Properties props = new Properties();
    props.setProperty("bootstrap.servers", "localhost:9092");
    props.setProperty("group.id", "billing");
    props.setProperty("enable.auto.commit", "false");
    props.setProperty("auto.commit.interval.ms", "1000");
    props.setProperty("key.deserializer",
        "org.apache.kafka.common.serialization.StringDeserializer");
    props.setProperty("value.deserializer",
        "org.apache.kafka.common.serialization.StringDeserializer");
    return props;
  }

}
