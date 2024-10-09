package pl.org.akai.plury_rentalis_backend.authorize;

public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException() {
        super();
    }

    public UnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
