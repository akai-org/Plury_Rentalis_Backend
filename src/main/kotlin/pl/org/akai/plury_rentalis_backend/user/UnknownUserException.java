package pl.org.akai.plury_rentalis_backend.user;

public class UnknownUserException extends RuntimeException {
    public UnknownUserException(String message) {
        super(message);
    }

    public UnknownUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
