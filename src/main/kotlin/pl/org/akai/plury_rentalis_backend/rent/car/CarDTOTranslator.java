package pl.org.akai.plury_rentalis_backend.rent.car;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CarDTOTranslator {
    @Value("${net.endpoints.get-image}")
    private String urlGetImageByNameEndpoint = "";

    @Nullable
    public CarDTO carToDTO(Car car) {
        if (car == null) return null;

        return CarDTO.builder()
                .Id(car.getId())
                .imageURL(urlGetImageByNameEndpoint + "/" + car.getImageName())
                .name(car.getName())
                .build();
    }

    @Nullable
    public Car DTOToCar(CarDTO carDTO) {
        if (carDTO == null) return null;

        return Car.builder()
                .id(carDTO.getId())
                .imageName(carDTO.getImageURL().replaceAll("[^/\\\\]*[/\\\\]", ""))
                .name(carDTO.getName())
                .build();

    }
}
