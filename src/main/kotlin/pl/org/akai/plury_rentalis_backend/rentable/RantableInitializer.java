package pl.org.akai.plury_rentalis_backend.rentable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RantableInitializer implements CommandLineRunner {
    private final RentableRepository rentableRepository;

    @Autowired
    public RantableInitializer(RentableRepository rentableRepository) {
        this.rentableRepository = rentableRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        rentableRepository.save(new RentableObject(null, RentableType.Car, "PZ 12345"));
        rentableRepository.save(new RentableObject(null, RentableType.Car, "PZ 23456"));
        rentableRepository.save(new RentableObject(null, RentableType.Car, "PZ 34567"));
        rentableRepository.save(new RentableObject(null, RentableType.Car, "PZ 45678"));

        rentableRepository.save(new RentableObject(null, RentableType.Transmitter, "Hyundai c-1"));
        rentableRepository.save(new RentableObject(null, RentableType.Transmitter, "Hyundai c-2"));
        rentableRepository.save(new RentableObject(null, RentableType.Transmitter, "Hyundai c-3"));
        rentableRepository.save(new RentableObject(null, RentableType.Transmitter, "Hyundai c-4"));

        rentableRepository.save(new RentableObject(null, RentableType.Camera, "Sony test-5"));
        rentableRepository.save(new RentableObject(null, RentableType.Camera, "Sony test-6"));
        rentableRepository.save(new RentableObject(null, RentableType.Camera, "Sony test-7"));
        rentableRepository.save(new RentableObject(null, RentableType.Camera, "Sony test-8"));


    }
}
