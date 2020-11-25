package br.com.zup.casadocodigo.state;

import br.com.zup.casadocodigo.country.Country;
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
import java.util.function.LongFunction;

@RestController
@RequestMapping("/state")
public class NewStateController {

    private final EntityManager entityManager;

    public NewStateController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(@Valid @RequestBody NewStateRequest newStateRequest,
                                       UriComponentsBuilder uriComponentsBuilder) {

        LongFunction<Country> countryLoader = (long id) -> this.entityManager.getReference(Country.class, id);

        State state = newStateRequest.toModel(countryLoader);
        this.entityManager.persist(state);

        URI uri = uriComponentsBuilder
                .path("/state/{id}")
                .buildAndExpand(state.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
