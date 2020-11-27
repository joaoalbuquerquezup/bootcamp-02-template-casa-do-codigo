package br.com.zup.casadocodigo.purchase;

import br.com.zup.casadocodigo.book.Book;
import br.com.zup.casadocodigo.country.Country;
import br.com.zup.casadocodigo.coupon.Coupon;
import br.com.zup.casadocodigo.coupon.CouponRepository;
import br.com.zup.casadocodigo.state.State;
import br.com.zup.casadocodigo.validation.CountryHasStateValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.function.Function;
import java.util.function.LongFunction;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final EntityManager entityManager;
    private final CountryHasStateValidator countryHasStateValidator;
    private final CouponRepository couponRepository;

    public PurchaseController(EntityManager entityManager,
                              CountryHasStateValidator countryHasStateValidator,
                              CouponRepository couponRepository) {
        this.entityManager = entityManager;
        this.countryHasStateValidator = countryHasStateValidator;
        this.couponRepository = couponRepository;
    }

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(this.countryHasStateValidator);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(@Valid @RequestBody NewPurchaseRequest newPurchaseRequest) {

        LongFunction<Country> countryLoader = (long id) -> this.entityManager.getReference(Country.class, id);
        LongFunction<State> stateLoader = (long id) -> this.entityManager.getReference(State.class, id);
        LongFunction<Book> bookLoader = (long id) -> this.entityManager.getReference(Book.class, id);
        Function<String, Coupon> couponLoaderByCode = couponRepository::findByCode;

        Purchase purchase = newPurchaseRequest.toModel(countryLoader, stateLoader, bookLoader, couponLoaderByCode);

        this.entityManager.persist(purchase);

        return ResponseEntity.ok().build();
    }
}
