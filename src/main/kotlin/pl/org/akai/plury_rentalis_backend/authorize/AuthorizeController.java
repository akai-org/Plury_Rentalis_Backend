package pl.org.akai.plury_rentalis_backend.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthorizeController {
    private final AuthorizeService authorizeService;

    @Autowired
    public AuthorizeController(AuthorizeService authorizeService) {
        this.authorizeService = authorizeService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> authorizeByEmail(@RequestBody EmailHolder email) {
        return authorizeService.login(email.getEmail());
    }

    @GetMapping
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("{\"message\": \"Request test was successful\"}");
    }
}
