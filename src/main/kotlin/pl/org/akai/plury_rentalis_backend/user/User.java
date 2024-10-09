package pl.org.akai.plury_rentalis_backend.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import pl.org.akai.plury_rentalis_backend.config.UUIDGenerator;

@Entity(name = "rentalis_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name = "uuid", type = UUIDGenerator.class)
    private String uuid;

    @Column(unique = true, nullable = false, updatable = false, length = 50)
    @NonNull
    private String email;

    @Column
    private String name;
}
