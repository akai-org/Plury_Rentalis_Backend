package pl.org.akai.plury_rentalis_backend.rent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.org.akai.plury_rentalis_backend.rentable.RentableObject;
import pl.org.akai.plury_rentalis_backend.user.User;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @ManyToOne
    private User renter;

    @ManyToOne
    private RentableObject rentable;

    @Column
    private Instant rentDate;

    @Column
    @Setter
    private Instant returnDate;

    @Builder(builderMethodName = "builderWithRentDate")
    public Rent(User renter, RentableObject rentable, Instant rentDate) {
        this.renter = renter;
        this.rentable = rentable;
        this.rentDate = rentDate;
    }

    @Builder
    public Rent(User renter, RentableObject rentable) {
        this.renter = renter;
        this.rentable = rentable;
        this.rentDate = Instant.now();
    }
}
