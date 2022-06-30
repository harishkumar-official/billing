package billing.model;

import java.util.Map;
import lombok.Data;

@Data
public class EventEntity {

  String uuid;
  Map<String, Object> tags;
  long timestamp;
  String measurementName;
  Object measurementValue;
}
