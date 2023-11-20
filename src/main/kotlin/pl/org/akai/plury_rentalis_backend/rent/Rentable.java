package pl.org.akai.plury_rentalis_backend.rent;

import java.util.Map;

public interface Rentable<T> {
    Map<String, ?> getSpec();
    String getName();

    T getId();

    RentableType getType();

    String getImageURL();
}
