package pl.org.akai.plury_rentalis_backend.verify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyService {
    private final UserRepository userRepository;

    @Autowired
    public VerifyService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean verify(Verifiable verifiable) {
        return userRepository.existsByEmail(verifiable.getEmail());
    }
}
