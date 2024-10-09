package pl.org.akai.plury_rentalis_backend.rent;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.org.akai.plury_rentalis_backend.rentable.RentableObject;
import pl.org.akai.plury_rentalis_backend.user.User;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface RentRepository extends CrudRepository<RentDetails, String> {
    List<RentDetails> findAllByReturnDateIsNull();

    @Modifying
    @Transactional
    @Query("UPDATE RentDetails r SET r.renter = ?2, r.rentable = ?3, r.rentDate = ?4, r.returnDate = ?5 WHERE r.uuid = ?1")
    void updateRentDetailsByUuid(String uuid, User renter, RentableObject rentable, Instant rentDate, Instant returnDate);

    @Transactional
    default void updateRentDetailsByUuid(String uuid, RentDetails rentDetails) {
        updateRentDetailsByUuid(uuid, rentDetails.getRenter(), rentDetails.getRentable(), rentDetails.getRentDate(), rentDetails.getReturnDate());
    }

    List<RentDetails> findAllByRenterAndReturnDateIsNull(@NotNull User renter);

//    @Query("SELECT CASE WHEN r.returnDate IS NULL THEN true ELSE false END FROM RentDetails r WHERE r.uuid = ?1")
    boolean existsByRentableAndReturnDateIsNull(RentableObject rentable);

    Optional<RentDetails> findByRentableAndReturnDateIsNull(RentableObject rentable);

    Optional<RentDetails> findByUuid(String uuid);
}
