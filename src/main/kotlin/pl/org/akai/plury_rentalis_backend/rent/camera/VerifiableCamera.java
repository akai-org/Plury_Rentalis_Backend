package pl.org.akai.plury_rentalis_backend.rent.camera;

import lombok.Getter;
import lombok.Setter;
import pl.org.akai.plury_rentalis_backend.verify.Verifiable;

@Getter
@Setter
public class VerifiableCamera extends CameraDTO implements Verifiable {
    private String email;

    public VerifiableCamera(Long Id, String name, String imageURL, String email) {
        super(Id, name, imageURL);
        this.email = email;
    }

    public VerifiableCamera() {
        super();
    }
}
