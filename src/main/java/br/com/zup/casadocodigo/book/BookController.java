package br.com.zup.casadocodigo.book;

import br.com.zup.casadocodigo.author.Author;
import br.com.zup.casadocodigo.book.create.CreateBookRequest;
import br.com.zup.casadocodigo.book.list.ListBookResponse;
import br.com.zup.casadocodigo.book.view.ViewBookResponse;
import br.com.zup.casadocodigo.category.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.function.LongFunction;

@RestController
@RequestMapping("/book")
public class BookController {

    private final EntityManager entityManager;

    public BookController(EntityManager entityManager) {
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

    /**
     * TODO: Deveria ser incluída a paginação
     */
    @GetMapping
    public ResponseEntity<List<ListBookResponse>> list () {

        String sql = "SELECT new ListBookResponse(b.id, b.title) FROM Book b";

        List<ListBookResponse> listBookResponse = this.entityManager
                .createQuery(sql,  ListBookResponse.class)
                .getResultList();

        return ResponseEntity.ok(listBookResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewBookResponse> view (@PathVariable Long id) {
        String sql = "SELECT new br.com.zup.casadocodigo.book.view.ViewBookResponse(" +
                                " b.title, b.summary, b.index, b.price, b.totalPages, b.isbn, b.publishDate," +
                                " c.name," +
                                " a.name, a.description)" +
                    " FROM Book b " +
                    " INNER JOIN b.author a" +
                    " INNER JOIN b.category c" +
                    " WHERE b.id = :id";

        ViewBookResponse viewBookResponse = this.entityManager
                            .createQuery(sql, ViewBookResponse.class)
                            .setParameter("id", id)
                            .getSingleResult();

        return ResponseEntity.ok(viewBookResponse);
    }

}
