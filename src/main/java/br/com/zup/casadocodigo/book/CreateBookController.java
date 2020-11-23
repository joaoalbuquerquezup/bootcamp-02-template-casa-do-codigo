package br.com.zup.casadocodigo.book;

import br.com.zup.casadocodigo.author.Author;
import br.com.zup.casadocodigo.category.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.function.LongFunction;

@RestController
@RequestMapping("/book")
public class CreateBookController {

    private final EntityManager entityManager;

    public CreateBookController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create (@Valid @RequestBody CreateBookRequest createBookRequest) {
        LongFunction<Category> categoryLoader = (long id) -> this.entityManager.getReference(Category.class, id);
        LongFunction<Author> authorLoader = (long id) -> this.entityManager.getReference(Author.class, id);

        Book book = createBookRequest.toModel(categoryLoader, authorLoader);
        this.entityManager.persist(book);

        return ResponseEntity.ok().build();
    }
}
