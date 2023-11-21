package pl.org.akai.plury_rentalis_backend.rent;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.org.akai.plury_rentalis_backend.rent.camera.CameraRepository;
import pl.org.akai.plury_rentalis_backend.rent.camera.VerifiableCamera;
import pl.org.akai.plury_rentalis_backend.rent.car.CarRepository;
import pl.org.akai.plury_rentalis_backend.rent.car.VerifiableCar;
import pl.org.akai.plury_rentalis_backend.verify.UnknownUserException;
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


//    public RentController(CarRepository carRepository, CameraRepository cameraRepository, RentedDataRepository rentedDataRepository, VerifyService verifyService) {
//        this.carRepository = carRepository;
//        this.cameraRepository = cameraRepository;
//        this.rentedDataRepository = rentedDataRepository;
//        this.verifyService = verifyService;
//    }

    @PostMapping("/car")
    public ResponseEntity<?> rentCar(@RequestBody VerifiableCar car) {
        if (!verifyService.verify(car))
            throw new UnknownUserException("User: " + car.getEmail() + " not found");

        if (!carRepository.existsByIdAndName(car.getId(), car.getName()))
            throw new RentableNotFoundException("Unknown car");

        RentData rentData = RentData.builder()
                .rentedId(car.getId())
                .rentDate(LocalDate.now())
                .type(RentableType.CAR)
                .build();

        rentedDataRepository.save(rentData);

        return ResponseEntity.ok().body(rentData);
    }

    @PostMapping("/camera")
    public ResponseEntity<?> rentCamera(@RequestBody VerifiableCamera camera) {
        if (!verifyService.verify(camera))
            throw new UnknownUserException("User: " + camera.getEmail() + " not found");

        if (!cameraRepository.existsByIdAndName(camera.getId(), camera.getName()))
            throw new RentableNotFoundException("Unknown camera");

        RentData rentData = RentData.builder()
                .rentedId(camera.getId())
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
    public ResponseEntity<?> handleNotFoundException(RuntimeException ex) {
        return ResponseEntity.notFound().build();
    }


}
