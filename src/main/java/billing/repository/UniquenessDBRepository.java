package billing.repository;

public interface UniquenessDBRepository {

  boolean exist(String uid);

  boolean save(String uid);
}
