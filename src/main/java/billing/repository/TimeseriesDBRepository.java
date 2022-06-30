package billing.repository;

import billing.model.TimeseriesDBEntity;
import java.util.List;

public interface TimeseriesDBRepository {

  boolean saveAll(List<TimeseriesDBEntity> records);
}
