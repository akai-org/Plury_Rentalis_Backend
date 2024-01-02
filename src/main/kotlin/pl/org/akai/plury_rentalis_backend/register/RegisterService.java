package pl.org.akai.plury_rentalis_backend.register;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.org.akai.plury_rentalis_backend.verify.User;
import pl.org.akai.plury_rentalis_backend.verify.UserRepository;
import pl.org.akai.plury_rentalis_backend.verify.VerifiableRent;

@Service
@AllArgsConstructor
public class RegisterService {
    private UserRepository userRepo;

    public ResponseEntity<?> registerNewUser(VerifiableRent<?> verifiable) {
        if (userRepo.existsByEmail(verifiable.getEmail()))
            return ResponseEntity.badRequest().body("User already exists");

        try {
            var user = User.builder().email(verifiable.getEmail()).build();
            userRepo.save(user);

            return ResponseEntity.ok().body(user);

        } catch (NullPointerException nullPointerException) {
            return ResponseEntity.badRequest().body("Email cannot be null");
        }
    }

    public Boolean isVerified(VerifiableRent<?> verifiable) {
        return userRepo.existsByEmail(verifiable.getEmail());
    }

    public User getUser(String email) {
        return userRepo.findByEmail(email);
    }

}
