package pl.org.akai.plury_rentalis_backend.rent.camera;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.org.akai.plury_rentalis_backend.rent.Rentable;
import pl.org.akai.plury_rentalis_backend.rent.RentableType;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CameraDTO implements Rentable<Long> {
    private Long Id;

    private String name;

    private String imageURL;

    @Override
    public Map<String, ?> getSpec() {
        return new HashMap<>();
    }

    @Override
    public RentableType getType() {
        return RentableType.CAMERA;
    }
}
