package billing.model;

import java.util.Map;

public class TimeseriesDBEntity {

  Map<String, Object> tags;
  long timestamp;
  String measurementName;
  Object measurementValue;
}
