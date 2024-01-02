package pl.org.akai.plury_rentalis_backend.rent;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.org.akai.plury_rentalis_backend.register.RegisterService;
import pl.org.akai.plury_rentalis_backend.rentable.RentableObject;
import pl.org.akai.plury_rentalis_backend.rentable.RentableRepository;
import pl.org.akai.plury_rentalis_backend.verify.VerifiableRent;

import java.time.LocalDate;
import java.util.stream.Stream;

@Controller
@RequestMapping("rent")
@AllArgsConstructor
public class RentController {
    private RentableRepository rentableRepository;
    private RentRepository rentRepository;
    private RegisterService registerService;

    @GetMapping("all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(rentableRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> rent(@RequestBody VerifiableRent<RentableObject> rentRequest) {
        if (!registerService.isVerified(rentRequest)) {
            return ResponseEntity.badRequest().body("Unknown user");
        }

        var iterator = rentRepository.findAll().iterator();
        var activeRent = Stream.generate(() -> null)
                .map(n -> iterator.next())
                .filter(it -> it.getRentable().getId().equals(rentRequest.getBody().getId()))
                .filter(it -> it.getReturnDate() == null)
                .findAny();

        if (activeRent.isPresent())
            return ResponseEntity.badRequest().body("Unknown user");

        Rent rent = Rent
                .builder()
                .rentDate(LocalDate.now())
                .renter(registerService.getUser(rentRequest.getEmail()))
                .rentable(rentRequest.getBody())
                .build();

        rentRepository.save(rent);

        return ResponseEntity.ok(rent);
    }

    @DeleteMapping
    public ResponseEntity<?> giveBack(@RequestBody VerifiableRent<Long> giveBackRequest) {
        if (!registerService.isVerified(giveBackRequest))
            return ResponseEntity.badRequest().body("Unknown user");

        var activeRent = rentRepository.findById(giveBackRequest.getBody());

        if (activeRent.isEmpty())
            return ResponseEntity.badRequest().body("Unknown rent id");

        Rent rent = activeRent.get();
        rent.setRentDate(LocalDate.now());

        rentRepository.save(rent);

        return ResponseEntity.ok(rent);
    }
}
