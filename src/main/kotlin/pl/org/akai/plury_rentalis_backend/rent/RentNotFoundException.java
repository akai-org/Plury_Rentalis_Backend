package pl.org.akai.plury_rentalis_backend.rent;

public class RentNotFoundException extends RuntimeException {
    public RentNotFoundException(String message) {
        super(message);
    }
    public RentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentNotFoundException() {
    }
}
