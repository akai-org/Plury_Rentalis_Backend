package pl.org.akai.plury_rentalis_backend.verify;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyService {
    private final UserRepository userRepository;


    public boolean verify(Verifiable verifiable) {
        return userRepository.existsByEmail(verifiable.getEmail());
    }

    public User findByEmail(String email) {
        return userRepository.getByEmail(email);
    }
}
