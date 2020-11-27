package br.com.zup.casadocodigo.purchase.item;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ViewPurchaseItemResponse {

    private String bookTitle;
    private Long amount;

    public ViewPurchaseItemResponse(PurchaseItem purchaseItem) {
        this.bookTitle = purchaseItem.getBook().getTitle();
        this.amount = purchaseItem.getAmount();
    }
}
