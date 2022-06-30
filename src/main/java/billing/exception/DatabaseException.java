package billing.exception;

public class DatabaseException extends RuntimeException {

  public DatabaseException(String message) {
    super(message);
  }

  public DatabaseException(Exception ex) {
    super(ex);
  }

  public DatabaseException(String message, Exception ex) {
    super(message, ex);
  }

}
