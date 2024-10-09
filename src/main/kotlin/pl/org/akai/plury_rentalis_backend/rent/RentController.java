package pl.org.akai.plury_rentalis_backend.rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rents")
public class RentController {
    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping("rent")
    public ResponseEntity<String> rent(@RequestHeader("Authorization") String authorizationHeader, @RequestBody String rentableUUID) {
        return rentService.rent(authorizationHeader, rentableUUID);
    }

    @PostMapping("return")
    public ResponseEntity<String> returnRent(@RequestHeader("Authorization") String authorizationHeader, @RequestBody String rentUUID) {
        return rentService.returnObject(authorizationHeader, rentUUID);
    }

    @GetMapping("rents")
    public ResponseEntity<List<RentDetails>> getActiveRents() {
        return rentService.getActiveRents();
    }

    @GetMapping("rentable")
    public ResponseEntity<List<Rent>> getRentable(@RequestParam(required = false) String by) {
        if (by == null) return rentService.getAllRentableAsRents();
        else return rentService.getAllRentableAsRentsRentedBy(by);
    }

    @GetMapping("rent")
    public ResponseEntity<RentDetails> getRent(@RequestParam String rentUUID) {
        return rentService.getRentByUuid(rentUUID);
    }
}
