package br.com.zup.casadocodigo.validation;

import br.com.zup.casadocodigo.book.Book;
import br.com.zup.casadocodigo.purchase.NewPurchaseRequest;
import br.com.zup.casadocodigo.purchase.item.NewPurchaseItemRequest;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.List;

public class PurchaseTotalPriceValidator implements ConstraintValidator<PurchaseTotalPrice, NewPurchaseRequest> {

    private final EntityManager entityManager;

    public PurchaseTotalPriceValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean isValid(NewPurchaseRequest newShoppingCartRequest, ConstraintValidatorContext context) {

        BigDecimal totalRequest = newShoppingCartRequest.getTotal().setScale(2);

        List<NewPurchaseItemRequest> newPurchaseItemRequest = newShoppingCartRequest.getShoppingCartItemsRequest();

        BigDecimal realTotal = newPurchaseItemRequest
                .stream()
                .map(item -> {
                    Book book = this.entityManager.find(Book.class, item.getBookId());
                    BigDecimal amount = new BigDecimal(item.getAmount());
                    return book.getPrice().multiply(amount);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        String defaultMessage = context.getDefaultConstraintMessageTemplate();
        context.disableDefaultConstraintViolation();

        context.buildConstraintViolationWithTemplate(defaultMessage)
                .addPropertyNode("total")
                .addConstraintViolation();

        return totalRequest.equals(realTotal); // Usar o compareTo
    }
}
