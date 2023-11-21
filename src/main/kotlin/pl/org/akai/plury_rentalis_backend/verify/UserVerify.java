package pl.org.akai.plury_rentalis_backend.verify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.org.akai.plury_rentalis_backend.verify.Verifiable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserVerify implements Verifiable {
    private String email;
}
