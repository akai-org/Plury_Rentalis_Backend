package pl.org.akai.plury_rentalis_backend.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.org.akai.plury_rentalis_backend.user.UserRepository;

@Service
public class AuthorizeService {
    private final UserRepository userRepo;

    @Autowired
    public AuthorizeService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public ResponseEntity<?> login(String email) {
        if (userRepo.existsByEmail(email))
            return ResponseEntity.ok().body(getToken(email));

        return ResponseEntity.badRequest().body("User does not exist");
    }

    public AuthDataModel getToken(String email) {
        return new AuthDataModel(TokenGenerator.generateToken(email));
    }


}
