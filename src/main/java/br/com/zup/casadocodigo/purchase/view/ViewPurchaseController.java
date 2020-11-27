package br.com.zup.casadocodigo.purchase.view;

import br.com.zup.casadocodigo.purchase.Purchase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/purchase")
public class ViewPurchaseController {

    private final EntityManager entityManager;

    public ViewPurchaseController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GetMapping("/{purchaseId}")
    @Transactional
    public ResponseEntity<ViewPurchaseResponse> view(@PathVariable Long purchaseId) {

        // Pegar a mensagem via Message Source. Ou enviar só a chave pra pegar via msg source no handler
        Purchase purchase = Optional
                .ofNullable(this.entityManager.find(Purchase.class, purchaseId))
                .orElseThrow(() -> new IllegalArgumentException("Id inválido"));

        // Fazer DTO Projection
        ViewPurchaseResponse viewPurchaseResponse = new ViewPurchaseResponse(purchase);

        return ResponseEntity.ok(viewPurchaseResponse);
    }
}
