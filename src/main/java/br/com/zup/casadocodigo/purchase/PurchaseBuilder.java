package br.com.zup.casadocodigo.purchase;

import br.com.zup.casadocodigo.country.Country;
import br.com.zup.casadocodigo.coupon.Coupon;
import br.com.zup.casadocodigo.purchase.cartitem.PurchaseItem;
import br.com.zup.casadocodigo.state.State;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseBuilder {

    private String email;
    private String firstName;
    private String lastName;
    private String document;
    private String address;
    private String complement;
    private String city;
    private State state;
    private Country country;
    private String phone;
    private String cep;
    private BigDecimal total;
    private List<PurchaseItem> purchaseItemList;
    private Coupon coupon;

    public static PurchaseBuilder aPurchase() {
        return new PurchaseBuilder();
    }

    public PurchaseBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public PurchaseBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PurchaseBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PurchaseBuilder withDocument(String document) {
        this.document = document;
        return this;
    }

    public PurchaseBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public PurchaseBuilder withComplement(String complement) {
        this.complement = complement;
        return this;
    }

    public PurchaseBuilder withCity(String city) {
        this.city = city;
        return this;
    }

    public PurchaseBuilder withState(State state) {
        this.state = state;
        return this;
    }

    public PurchaseBuilder withCountry(Country country) {
        this.country = country;
        return this;
    }

    public PurchaseBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public PurchaseBuilder withCep(String cep) {
        this.cep = cep;
        return this;
    }

    public PurchaseBuilder withTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public PurchaseBuilder withPurchaseItemList(List<PurchaseItem> purchaseItemList) {
        this.purchaseItemList = purchaseItemList;
        return this;
    }

    public Purchase build() {
        return new Purchase(
                this.email,
                this.firstName,
                this.lastName,
                this.document,
                this.address,
                this.complement,
                this.city,
                this.state,
                this.country,
                this.phone,
                this.cep,
                this.total,
                this.purchaseItemList
        );
    }
}