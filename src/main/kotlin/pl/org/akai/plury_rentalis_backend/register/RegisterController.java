package pl.org.akai.plury_rentalis_backend.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.org.akai.plury_rentalis_backend.verify.UserRepository;
import pl.org.akai.plury_rentalis_backend.verify.VDTOToUser;
import pl.org.akai.plury_rentalis_backend.verify.VerifyDTO;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserRepository userRepo;

    @PostMapping
    ResponseEntity<?> registerNewUser(@RequestBody VerifyDTO verifyDTO) {
        if (userRepo.existsByEmail(verifyDTO.getEmail())) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        try {
            var user = VDTOToUser.toUser(verifyDTO);
            userRepo.save(user);
            return ResponseEntity.ok().body(user);
        } catch (NullPointerException nullPointerException) {
            return ResponseEntity.badRequest().body("Email cannot be null");
        }
    }

}
