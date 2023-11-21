package pl.org.akai.plury_rentalis_backend.register;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.org.akai.plury_rentalis_backend.verify.UserRepository;
import pl.org.akai.plury_rentalis_backend.verify.UserVerify;
import pl.org.akai.plury_rentalis_backend.verify.VerifiableUserTranslator;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private final UserRepository userRepo;

    public RegisterController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping
    public ResponseEntity<?> registerNewUser(@NotNull @RequestBody UserVerify userVerify) {
        if (userRepo.existsByEmail(userVerify.getEmail())) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        var user = VerifiableUserTranslator.toUser(userVerify);
        userRepo.save(user);
        return ResponseEntity.ok().body(user);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> carryException(NullPointerException ex) {
        return ResponseEntity.badRequest().body("Email cannot be null");
    }
}
