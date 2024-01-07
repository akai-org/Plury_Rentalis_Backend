package pl.org.akai.plury_rentalis_backend.rent.comment;

import kotlin.Pair;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.org.akai.plury_rentalis_backend.register.RegisterService;
import pl.org.akai.plury_rentalis_backend.rent.Rent;
import pl.org.akai.plury_rentalis_backend.rent.RentRepository;
import pl.org.akai.plury_rentalis_backend.rentable.RentableRepository;
import pl.org.akai.plury_rentalis_backend.verify.VerifiableRent;

import java.time.LocalDate;
import java.util.stream.StreamSupport;

@RestController
@AllArgsConstructor
@RequestMapping("comment")
public class CommentController {
    private CommentRepository commentRepository;
    private RegisterService registerService;
    private RentRepository rentRepository;
    private RentableRepository rentableRepository;


    @PostMapping
    public ResponseEntity<?> postComment(@RequestBody VerifiableRent<Pair<? extends Rent, String>> comment) {
        if(!registerService.isVerified(comment.getEmail()))
            return ResponseEntity.badRequest().body("Unknown user");

        final var rent = comment.getBody().getFirst();
        final var rentCompare = rentRepository.findById(rent.getId());
        if (rentCompare.isEmpty() || !rentCompare.get().equals(rent))
            return ResponseEntity.badRequest().body("Incorrect rent");


        var newComment = new Comment(
                null,
                rent,
                LocalDate.now(),
                comment.getBody().getSecond(),
                registerService.getUser(comment.getEmail())
        );
        commentRepository.save(newComment);

        return ResponseEntity.ok(newComment);
    }

    @GetMapping("all")
    public ResponseEntity<?> getComments() {
        return ResponseEntity.ok(commentRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Long id) {
        if(!commentRepository.existsById(id))
            return ResponseEntity.badRequest().body("Unknown comment id");

        return ResponseEntity.ok(commentRepository.findById(id));
    }

    @GetMapping("{author}")
    public ResponseEntity<?> getCommentByAuthor(@PathVariable String author) {
        if (registerService.isVerified(author))
            return ResponseEntity.badRequest().body("Unknown user");


        return ResponseEntity.ok(
                StreamSupport.stream(commentRepository.findAll().spliterator(), false)
                .filter(it-> it.getAuthor().getEmail().equals(author))
                .toList()
        );
    }

    @GetMapping("for/{rentableId}")
    public ResponseEntity<?> getCommentForObject(@PathVariable String rentableId) {
        if (!rentableRepository.existsById(rentableId))
            return ResponseEntity.badRequest().body("Unknown object");

        return ResponseEntity.ok(
                StreamSupport.stream(commentRepository.findAll().spliterator(), false)
                .filter(it-> it.getRent().getRentable().getId().equals(rentableId))
                .toList()
        );
    }
}
