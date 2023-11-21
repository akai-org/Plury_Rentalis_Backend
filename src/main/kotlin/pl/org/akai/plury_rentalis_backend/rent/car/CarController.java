package pl.org.akai.plury_rentalis_backend.rent.car;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("cars/")
@RequiredArgsConstructor
public class CarController {
    private final CarRepository carRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(List.of(carRepository.findAll()));
    }
}
