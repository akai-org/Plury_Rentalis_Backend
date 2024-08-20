package pl.org.akai.plury_rentalis_backend.rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.org.akai.plury_rentalis_backend.authorize.AuthorizeService;
import pl.org.akai.plury_rentalis_backend.authorize.Token;
import pl.org.akai.plury_rentalis_backend.rentable.RentableNotFoundException;
import pl.org.akai.plury_rentalis_backend.rentable.RentableRepository;
import pl.org.akai.plury_rentalis_backend.user.UserRepository;

import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RentService {
    private final UserRepository userRepository;
    private final AuthorizeService authorizeService;
    private final RentRepository rentRepository;
    private final RentableRepository rentableRepository;

    @Autowired
    public RentService(UserRepository userRepository, AuthorizeService authorizeService, RentRepository rentRepository, RentableRepository rentableRepository) {
        this.userRepository = userRepository;
        this.authorizeService = authorizeService;
        this.rentRepository = rentRepository;
        this.rentableRepository = rentableRepository;
    }

    public ResponseEntity<?> rent(String authorizationHeader, String rentableUUID) {
        AtomicReference<Rent> rent = new AtomicReference<>();
        try {
            userRepository.findAllAsStream()
                    .findFirst(user ->
                            ((Token) Objects.requireNonNull(authorizeService.login(user.getEmail()).getBody()))
                                    .token()
                                    .equals(authorizationHeader)
                    ).ifPresentOrElse(user ->
                            {
                                if (!rentableRepository.existsById(rentableUUID))
                                    throw new RentableNotFoundException("Rentable under id: " + rentableUUID + " does not exist");

                                rent.set(Rent.builder()
                                        .rentable(rentableRepository.findById(rentableUUID).get())
                                        .renter(user)
                                        .build());

                                rentRepository.save(rent.get());
                            },
                            () ->
                            {
                                throw new RentableNotFoundException("User does not exist");
                            }
                    );
        }catch (RentableNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unknown error");
        }

        return ResponseEntity.ok(rent.get().getUuid());
    }

    public ResponseEntity<?> getRentByUuid(String rentUuid) {
        try {
            Rent rent = rentRepository
                    .findById(rentUuid)
                    .orElseThrow(() -> new RentNotFoundException("Rent under id: " + rentUuid + " does not exist"));

            return ResponseEntity.ok(rent);
        } catch (RentNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> getRents() {
        return ResponseEntity.ok(rentRepository.findAllByReturnDateIsEmpty());
    }

    public ResponseEntity<?> returnObject(String authorizationHeader, String rentUuid) {
        try {
            rentRepository.findById(rentUuid).ifPresentOrElse(rent -> {
                rent.setReturnDate(Instant.now());
                rentRepository.updateByUuid(rentUuid, rent);
            }, () -> {
                throw new RentNotFoundException("Rentable under id: " + rentUuid + " does not exist");
            });
        } catch (RentNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unknown error");
        }

        return ResponseEntity.ok("Object returned");
    }
}
