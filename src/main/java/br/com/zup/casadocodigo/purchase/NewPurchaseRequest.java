package br.com.zup.casadocodigo.purchase;

import br.com.zup.casadocodigo.book.Book;
import br.com.zup.casadocodigo.country.Country;
import br.com.zup.casadocodigo.purchase.cartitem.NewPurchaseItemRequest;
import br.com.zup.casadocodigo.purchase.cartitem.PurchaseItem;
import br.com.zup.casadocodigo.state.State;
import br.com.zup.casadocodigo.validation.CpfCnpj;
import br.com.zup.casadocodigo.validation.ExistentId;
import br.com.zup.casadocodigo.validation.PurchaseTotalPrice;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

import static br.com.zup.casadocodigo.purchase.PurchaseBuilder.aPurchase;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@PurchaseTotalPrice
public class NewPurchaseRequest {

    @NotBlank @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank @CpfCnpj
    private String document;

    @NotBlank
    private String address;

    @NotBlank
    private String complement;

    @NotBlank
    private String city;

    @ExistentId
    private Long stateId;

    @NotNull
    @ExistentId
    private Long countryId;

    @NotBlank
    private String phone;

    @NotBlank
    private String cep;

    @NotNull
    @Positive
    private BigDecimal total; // Não deveria ser enviado do Client

    @NotNull @Size(min = 1)
    @JsonProperty("items")
    private List<NewPurchaseItemRequest> purchaseItemRequestList;

    public Long getStateId() {
        return this.stateId;
    }

    public Long getCountryId() {
        return this.countryId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<NewPurchaseItemRequest> getShoppingCartItemsRequest() {
        return purchaseItemRequestList;
    }

    public Purchase toModel(LongFunction<Country> countryLoader,
                            LongFunction<State> stateLoader,
                            LongFunction<Book> bookLoader) {

        Country country = countryLoader.apply(this.countryId);
        State state = stateLoader.apply(this.stateId);

        List<PurchaseItem> purchaseItemList = this.purchaseItemRequestList
                .stream()
                .map(purchaseItemRequest -> purchaseItemRequest.toModel(bookLoader))
                .collect(Collectors.toList());

        PurchaseBuilder purchaseBuilder = aPurchase()
                .withEmail(this.email)
                .withFirstName(this.firstName)
                .withLastName(this.lastName)
                .withDocument(this.document)
                .withAddress(this.address)
                .withComplement(this.complement)
                .withCity(this.city)
                .withState(state)
                .withCountry(country)
                .withPhone(this.phone)
                .withCep(this.cep)
                .withTotal(this.total)
                .withPurchaseItemList(purchaseItemList);

        return purchaseBuilder.build();
    }
}
