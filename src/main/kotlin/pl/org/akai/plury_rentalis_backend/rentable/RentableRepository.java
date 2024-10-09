package pl.org.akai.plury_rentalis_backend.rentable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface RentableRepository extends CrudRepository<RentableObject, String> {
    @Transactional(readOnly = true)
    @Query("SELECT r FROM RentableObject r")
    Stream<RentableObject> findAllAsStream();

    Optional<RentableObject> findByUuid(String uuid);
}
