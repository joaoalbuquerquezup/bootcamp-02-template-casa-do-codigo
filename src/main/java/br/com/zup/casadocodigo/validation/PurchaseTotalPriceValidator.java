package br.com.zup.casadocodigo.validation;

import br.com.zup.casadocodigo.book.Book;
import br.com.zup.casadocodigo.purchase.shoppingcart.NewShoppingCartRequest;
import br.com.zup.casadocodigo.purchase.cartitem.ShoppingCartItemsRequest;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.List;

public class PurchaseTotalPriceValidator implements ConstraintValidator<PurchaseTotalPrice, NewShoppingCartRequest> {

    private final EntityManager entityManager;

    public PurchaseTotalPriceValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean isValid(NewShoppingCartRequest newShoppingCartRequest, ConstraintValidatorContext context) {

        BigDecimal totalRequest = newShoppingCartRequest.getTotal();
        List<ShoppingCartItemsRequest> shoppingCartItemsRequest = newShoppingCartRequest.getShoppingCartItemsRequest();

        BigDecimal realTotal = shoppingCartItemsRequest
                .stream()
                .map(ShoppingCartItemsRequest::getBookId)
                .map(bookId -> this.entityManager.find(Book.class, bookId))
                .map(Book::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalRequest.equals(realTotal);
    }
}
