package billing.exception;

public class ClientException extends RuntimeException {

  public ClientException(String message) {
    super(message);
  }

  public ClientException(Exception ex) {
    super(ex);
  }

  public ClientException(String message, Exception ex) {
    super(message, ex);
  }

}
