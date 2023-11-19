package pl.org.akai.plury_rentalis_backend.verify;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User getByEmail(String email);
    Boolean existsByEmail(String email);
}
