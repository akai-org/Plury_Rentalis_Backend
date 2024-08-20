package pl.org.akai.plury_rentalis_backend.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

public interface UserRepository extends CrudRepository<User, String> {

    User findByEmail(String email);
    Boolean existsByEmail(String email);

    @Transactional(readOnly = true)
    @Query("SELECT u FROM User u")
    Stream<User> findAllAsStream();
}
