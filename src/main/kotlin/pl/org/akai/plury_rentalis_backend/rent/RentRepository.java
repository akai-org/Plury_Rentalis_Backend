package pl.org.akai.plury_rentalis_backend.rent;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.org.akai.plury_rentalis_backend.rentable.RentableObject;

import java.util.List;

public interface RentRepository extends CrudRepository<Rent, String> {
    List<Rent> findAllByReturnDateIsEmpty();

    @Modifying
    Rent updateByUuid(String uuid, Rent rent);
}
