package pl.org.akai.plury_rentalis_backend.rent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.org.akai.plury_rentalis_backend.rentable.RentableObject;
import pl.org.akai.plury_rentalis_backend.verify.User;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Rent {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User renter;

    @ManyToOne
    private RentableObject rentable;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "YYYY-mm-DD")
    private LocalDate rentDate;

    @DateTimeFormat(pattern = "YYYY-mm-DD")
    private LocalDate returnDate;
}
