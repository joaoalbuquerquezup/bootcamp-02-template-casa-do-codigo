package br.com.zup.casadocodigo.country;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/country")
public class NewCountryController {

    private final EntityManager entityManager;

    public NewCountryController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(@Valid @RequestBody NewCountryRequest newCountryRequest,
                                         UriComponentsBuilder uriComponentsBuilder) {
        Country country = newCountryRequest.toModel();
        this.entityManager.persist(country);

        URI uri = uriComponentsBuilder
                .path("/country/{id}")
                .buildAndExpand(country.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
