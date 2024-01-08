package pl.org.akai.plury_rentalis_backend.verify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VerifiableRent<T> {
    private String email;
    private T body;
}
