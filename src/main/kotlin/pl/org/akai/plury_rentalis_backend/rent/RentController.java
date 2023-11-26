package pl.org.akai.plury_rentalis_backend.rent;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.org.akai.plury_rentalis_backend.rent.camera.Camera;
import pl.org.akai.plury_rentalis_backend.rent.camera.CameraRepository;
import pl.org.akai.plury_rentalis_backend.rent.car.Car;
import pl.org.akai.plury_rentalis_backend.rent.car.CarRepository;
import pl.org.akai.plury_rentalis_backend.verify.UnknownUserException;
import pl.org.akai.plury_rentalis_backend.verify.User;
import pl.org.akai.plury_rentalis_backend.verify.Verify;
import pl.org.akai.plury_rentalis_backend.verify.VerifyService;

import java.time.LocalDate;

@RestController
@RequestMapping("/rent")
@AllArgsConstructor
public class RentController {
    private final CarRepository carRepository;

    private final CameraRepository cameraRepository;

    private final RentedDataRepository rentedDataRepository;

    private final VerifyService verifyService;



    @PostMapping("/car")
    public ResponseEntity<?> rentCar(@RequestBody Verify<Car> car) {
        if (!verifyService.verify(car))
            throw new UnknownUserException("User: " + car.getEmail() + " not found");

        if (!carRepository.existsByIdAndName(car.getBody().getId(), car.getBody().getName()))
            throw new RentableNotFoundException("Unknown car");

        RentData rentData = RentData.builder()
                .rentedId(car.getBody().getId())
                .rentDate(LocalDate.now())
                .type(RentableType.CAR)
                .build();

        rentedDataRepository.save(rentData);

        return ResponseEntity.ok().body(rentData);
    }

    @PostMapping("/camera")
    public ResponseEntity<?> rentCamera(@RequestBody Verify<Camera> camera) {
        if (!verifyService.verify(camera))
            throw new UnknownUserException("User: " + camera.getEmail() + " not found");

        if (!cameraRepository.existsByIdAndName(camera.getBody().getId(), camera.getBody().getName()))
            throw new RentableNotFoundException("Unknown camera");

        User currentUser = verifyService.findByEmail(camera.getEmail());
        RentData rentData = RentData.builder()
                .rentedId(camera.getBody().getId())
                .renterId(currentUser.getId())
                .rentDate(LocalDate.now())
                .type(RentableType.CAMERA)
                .build();

        rentedDataRepository.save(rentData);

        return ResponseEntity.ok().body(rentData);
    }

    @ExceptionHandler({
            UnknownUserException.class,
            RentableNotFoundException.class
    })
    public ResponseEntity<?> handleNotFoundException(RuntimeException ignored) {
        return ResponseEntity.notFound().build();
    }


}
