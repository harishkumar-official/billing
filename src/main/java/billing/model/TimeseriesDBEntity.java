package billing.model;

import java.util.Map;
import lombok.Data;

@Data
public class TimeseriesDBEntity {

  Map<String, Object> tags;
  long timestamp;
  String measurementName;
  Object measurementValue;
}
