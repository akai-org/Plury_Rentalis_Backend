package pl.org.akai.plury_rentalis_backend.rent.camera;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cameras")
@RequiredArgsConstructor
public class CameraController {
    private final CameraRepository cameraRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(List.of(cameraRepository.findAll()));
    }
}
