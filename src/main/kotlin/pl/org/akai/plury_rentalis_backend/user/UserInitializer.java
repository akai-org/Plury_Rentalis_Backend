package pl.org.akai.plury_rentalis_backend.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserInitializer implements CommandLineRunner {
    private final UserRepository userRepository;

    public UserInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail("admin@test.com")) {
            User admin = new User();
            admin.setEmail("admin@test.com");
            admin.setName("Adam Małysz"); // Ensure to hash the password
            userRepository.save(admin);
        }
    }
}
