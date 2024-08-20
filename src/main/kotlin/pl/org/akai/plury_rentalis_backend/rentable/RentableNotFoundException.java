package pl.org.akai.plury_rentalis_backend.rentable;

public class RentableNotFoundException extends RuntimeException {
    public RentableNotFoundException(String message) {
        super(message);
    }

    public RentableNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
