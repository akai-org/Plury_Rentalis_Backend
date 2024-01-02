package pl.org.akai.plury_rentalis_backend.rentable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RentableObject {
    @Id
    @GeneratedValue
    private String id;

    @Column
    @Enumerated(EnumType.STRING)
    private RentableType type;
}
