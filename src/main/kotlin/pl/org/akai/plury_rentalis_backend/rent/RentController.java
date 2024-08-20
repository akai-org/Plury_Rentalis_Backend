package pl.org.akai.plury_rentalis_backend.rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rents")
public class RentController {
    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping("rent")
    public ResponseEntity<?> rent(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String rentableUUID) {
        return rentService.rent(authorizationHeader, rentableUUID);
    }

    @PostMapping("return")
    public ResponseEntity<?> returnRent(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String rentUUID) {
        return rentService.returnObject(authorizationHeader, rentUUID);
    }

    @GetMapping("rents")
    public ResponseEntity<?> getAllRented() {
        return rentService.getRents();
    }

    @GetMapping("rent")
    public ResponseEntity<?> getRent(@RequestParam String rentUUID) {
        return rentService.getRentByUuid(rentUUID);
    }
}
