package br.com.zup.casadocodigo.purchase.item;

import br.com.zup.casadocodigo.book.Book;
import br.com.zup.casadocodigo.validation.ExistentId;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.function.LongFunction;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class NewPurchaseItemRequest {

    @NotNull @ExistentId
    private Long bookId;

    @NotNull @Positive
    private Long amount;

    public Long getBookId() {
        return bookId;
    }

    public Long getAmount() {
        return amount;
    }

    public PurchaseItem toModel(LongFunction<Book> bookLoader) {
        Book book = bookLoader.apply(this.bookId);
        return new PurchaseItem(book, this.amount);
    }
}
