package br.com.zup.casadocodigo.author;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/author")
public class CreateAuthorController {

    private final EntityManager entityManager;

    public CreateAuthorController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create (@Valid @RequestBody CreateAuthorRequest createAuthorRequest) {

        Author author = createAuthorRequest.toModel();
        entityManager.persist(author);

        return ResponseEntity.ok().build();
    }
}
