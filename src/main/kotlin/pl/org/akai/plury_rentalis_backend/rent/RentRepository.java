package pl.org.akai.plury_rentalis_backend.rent;

import org.springframework.data.repository.CrudRepository;

public interface RentRepository extends CrudRepository<Rent, Long> {
    Iterable<Rent> findAllByReturnDateIsEmpty();
}
