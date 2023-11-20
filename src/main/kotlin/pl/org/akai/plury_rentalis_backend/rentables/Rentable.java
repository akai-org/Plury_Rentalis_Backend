package pl.org.akai.plury_rentalis_backend.rentables;

import java.util.Map;

public interface Rentable<T> {
    Map<String, ?> getSpec();
    String getName();

    T getId();

    byte[] getImage();
}
