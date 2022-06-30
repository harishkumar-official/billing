package billing.repository;

import billing.model.TimeseriesDBEntity;

public interface TimeseriesDBRepository {

  boolean save(String uid, TimeseriesDBEntity record);
}
