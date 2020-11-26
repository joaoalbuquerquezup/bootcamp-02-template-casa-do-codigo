package br.com.zup.casadocodigo.purchase.cartitem;

import br.com.zup.casadocodigo.book.Book;
import br.com.zup.casadocodigo.purchase.Purchase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Book book;

    @NotNull @Positive
    @Column(nullable = false)
    private Long amount;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Purchase purchase;

    /**
     * Hibernate usage only
     */
    @Deprecated
    protected PurchaseItem() { }

    public PurchaseItem(@NotNull Book book,
                        @NotNull @Positive Long amount) {
        this.book = book;
        this.amount = amount;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}
