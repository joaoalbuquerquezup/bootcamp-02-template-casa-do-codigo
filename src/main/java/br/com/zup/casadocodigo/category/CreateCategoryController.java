package br.com.zup.casadocodigo.category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CreateCategoryController {

    private final EntityManager entityManager;

    public CreateCategoryController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create (@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {

        Category category = createCategoryRequest.toModel();
        this.entityManager.persist(category);

        return ResponseEntity.ok().build();
    }


}
