package pl.org.akai.plury_rentalis_backend.register;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.org.akai.plury_rentalis_backend.user.UserDTO;

@RestController
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController {
    private RegisterService registerService;

    @PostMapping
    ResponseEntity<?> registerNewUser(@RequestBody UserDTO registeredUser) {
        return registerService.registerNewUser(registeredUser);
    }

}
