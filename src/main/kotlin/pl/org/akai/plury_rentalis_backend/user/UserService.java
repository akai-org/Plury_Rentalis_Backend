package pl.org.akai.plury_rentalis_backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.org.akai.plury_rentalis_backend.authorize.EmailHolder;
import pl.org.akai.plury_rentalis_backend.authorize.TokenGenerator;
import pl.org.akai.plury_rentalis_backend.authorize.UnauthorizedAccessException;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<User> getUserByEmail(String email, String token) {
        User user = getUserByEmail(email);
        if (TokenGenerator.generateToken(user.getEmail()).equals(token))
            return ResponseEntity.ok(user);
        else
            throw new UnauthorizedAccessException();
    }
}
