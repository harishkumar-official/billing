package billing.repository;

import java.util.List;

public interface UniquenessDBRepository {

  boolean exist(String uid);

  boolean saveAll(List<String> uid);
}
