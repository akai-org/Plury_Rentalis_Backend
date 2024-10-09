package pl.org.akai.plury_rentalis_backend.rent;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.org.akai.plury_rentalis_backend.authorize.AuthorizeService;
import pl.org.akai.plury_rentalis_backend.rentable.RentableNotFoundException;
import pl.org.akai.plury_rentalis_backend.rentable.RentableObject;
import pl.org.akai.plury_rentalis_backend.rentable.RentableRepository;
import pl.org.akai.plury_rentalis_backend.user.UnknownUserException;
import pl.org.akai.plury_rentalis_backend.user.User;
import pl.org.akai.plury_rentalis_backend.user.UserRepository;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
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

    @Transactional
    public ResponseEntity<String> rent(String authorizationHeader, String rentableUUID) {
        User user = userRepository.findAllAsStream()
                .filter(u -> authorizeService.getToken(u.getEmail()).token().equals(authorizationHeader))
                .findFirst()
                .orElseThrow(() -> new UnknownUserException("Unknown token: " + authorizationHeader));

        RentableObject rentableObject = rentableRepository
                .findById(rentableUUID)
                .orElseThrow(() -> new RentableNotFoundException("Unknown rentable UUID: " + rentableUUID));

        if (rentRepository.existsByRentableAndReturnDateIsNull(rentableObject))
            throw new RentNotFoundException("Rentable under id: " + rentableUUID + " is already rented");

        RentDetails rentDetails = RentDetails.builder().renter(user).rentable(rentableObject).build();
        rentRepository.save(rentDetails);

        return ResponseEntity.ok(rentDetails.getUuid());
    }

    public ResponseEntity<RentDetails> getRentByUuid(String rentUuid) {

        RentDetails rentDetails = rentRepository
                .findById(rentUuid)
                .orElseThrow(() -> new RentNotFoundException("Rent under id: " + rentUuid + " does not exist"));

        return ResponseEntity.ok(rentDetails);
    }

    public ResponseEntity<List<RentDetails>> getActiveRents() {
        return ResponseEntity.ok(rentRepository.findAllByReturnDateIsNull());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<Rent>> getAllRentableAsRents() {
        return ResponseEntity.ok(
                rentableRepository.findAllAsStream()
                        .map(this::getRent)
                        .toList()
        );
    }

    public ResponseEntity<String> returnObject(String authorizationHeader, String rentableUUID) {
        RentableObject rentableObject = rentableRepository
                .findByUuid(rentableUUID)
                .orElseThrow(() -> new RentableNotFoundException("Unknown rentable UUID: " + rentableUUID));

        RentDetails rentDetails = rentRepository
                .findByRentableAndReturnDateIsNull(rentableObject)
                .orElseThrow(() -> new RentNotFoundException("Rentable under id: " + rentableUUID + " is not rented"));

        rentDetails.setReturnDate(Instant.now());
        rentRepository.updateRentDetailsByUuid(rentDetails.getUuid(), rentDetails);

        return ResponseEntity.ok("Object returned");
    }

    @ExceptionHandler({
            RentNotFoundException.class,
            RentableNotFoundException.class,
            UnknownUserException.class
    })
    public ResponseEntity<String> handleRentNotFoundException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    private Rent getRent(RentableObject rentableObject) {
        var rent = rentRepository.findByRentableAndReturnDateIsNull(rentableObject);

        var rentDetails = rent.orElseGet(() -> createEmptyRent(rentableObject));

        return new Rent(rentDetails);
    }

    private RentDetails createEmptyRent(RentableObject rentableObject) {
        return RentDetails.builderWithRentDate()
                .rentable(rentableObject)
                .build();
    }

    public ResponseEntity<List<Rent>> getAllRentableAsRentsRentedBy(String renterUUID) {
        User renter = userRepository.findById(renterUUID).orElseThrow(UnknownUserException::new);

        return ResponseEntity.ok(rentRepository
                .findAllByRenterAndReturnDateIsNull(renter)
                .stream()
                .map(Rent::new)
                .toList()
        );
    }
}
