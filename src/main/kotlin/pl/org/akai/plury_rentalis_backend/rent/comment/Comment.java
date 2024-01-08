package pl.org.akai.plury_rentalis_backend.rent.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.org.akai.plury_rentalis_backend.rent.Rent;
import pl.org.akai.plury_rentalis_backend.verify.User;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "rent_id")
    private Rent rent;

    @DateTimeFormat(pattern="YYYY-mm-DD")
    private LocalDate date;

    @Column(nullable = false, length = 1000)
    public String content;

    @Setter
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

}
