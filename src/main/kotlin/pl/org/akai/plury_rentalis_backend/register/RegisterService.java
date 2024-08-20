package pl.org.akai.plury_rentalis_backend.register;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.org.akai.plury_rentalis_backend.authorize.AuthorizeService;
import pl.org.akai.plury_rentalis_backend.user.User;
import pl.org.akai.plury_rentalis_backend.user.UserDTO;
import pl.org.akai.plury_rentalis_backend.user.UserRepository;

@Service
@AllArgsConstructor
public class RegisterService {
    private UserRepository userRepo;
    private final AuthorizeService authorizeService;

    public ResponseEntity<?> registerNewUser(UserDTO newUser) {
        if (userRepo.existsByEmail(newUser.email()))
            return ResponseEntity.badRequest().body("User already exists");

        try {
            var user = User.builder()
                    .email(newUser.email())
                    .name(newUser.name())
                    .build();

            userRepo.save(user);

            return authorizeService.login(newUser.email());

        } catch (NullPointerException nullPointerException) {
            return ResponseEntity.badRequest().body("Email cannot be null");
        }
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

}
