package pl.org.akai.plury_rentalis_backend.rent.car;

import lombok.Getter;
import lombok.Setter;
import pl.org.akai.plury_rentalis_backend.verify.Verifiable;

@Getter
@Setter
public class VerifiableCar extends CarDTO implements Verifiable {
    private String email;

    public VerifiableCar(Long Id, String name, String imageURL, String email) {
        super(Id, name, imageURL);
        this.email = email;
    }

    public VerifiableCar() {
        super();
    }
}
