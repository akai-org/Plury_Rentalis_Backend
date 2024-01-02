package pl.org.akai.plury_rentalis_backend.rentable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentableRepository extends CrudRepository<RentableObject, String> {
}
