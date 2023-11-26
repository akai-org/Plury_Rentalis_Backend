package pl.org.akai.plury_rentalis_backend.verify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Verify<T> implements Verifiable {
    private String email;
    private T body;
}
