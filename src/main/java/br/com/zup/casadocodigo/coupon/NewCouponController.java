package br.com.zup.casadocodigo.coupon;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/coupon")
public class NewCouponController {

    private final EntityManager entityManager;

    public NewCouponController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(@Valid @RequestBody NewCouponRequest newCouponRequest) {
        Coupon coupon = newCouponRequest.toModel();
        this.entityManager.persist(coupon);

        return ResponseEntity.ok().build();
    }
}
