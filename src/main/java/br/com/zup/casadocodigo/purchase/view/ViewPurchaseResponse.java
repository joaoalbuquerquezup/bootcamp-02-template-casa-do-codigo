package br.com.zup.casadocodigo.purchase.view;

import br.com.zup.casadocodigo.coupon.Coupon;
import br.com.zup.casadocodigo.purchase.Purchase;
import br.com.zup.casadocodigo.purchase.item.ViewPurchaseItemResponse;
import br.com.zup.casadocodigo.state.State;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.CEILING;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ViewPurchaseResponse {

    private String email;
    private String firstName;
    private String lastName;
    private String document;
    private String address;
    private String complement;
    private String city;
    private String phone;
    private String cep;

    private String countryName;
    private String stateName;

    private BigDecimal value;
    private Boolean couponExists = false;
    private BigDecimal discountedValue;

    @JsonProperty("items")
    private List<ViewPurchaseItemResponse> viewPurchaseItemResponseList;

    public ViewPurchaseResponse(Purchase purchase) {

        this.email = purchase.getEmail();
        this.firstName = purchase.getFirstName();
        this.lastName = purchase.getLastName();
        this.document = purchase.getDocument();
        this.address = purchase.getAddress();
        this.complement = purchase.getComplement();
        this.city = purchase.getCity();
        this.phone = purchase.getPhone();
        this.cep = purchase.getCep();

        this.countryName = purchase.getCountry().getName();
        State state = purchase.getState();
        if (Objects.nonNull(state)) {  this.stateName = state.getName(); }

        this.value = purchase.getTotal();
        Coupon coupon = purchase.getCoupon();
        if (coupon != null) {
            this.couponExists = true;
            BigDecimal percentageDiscount = coupon.getPercentageDiscount();
            BigDecimal divided = percentageDiscount.divide(new BigDecimal(100), CEILING);
            var percentage = ONE.subtract(divided);
            this.discountedValue = this.value.multiply(percentage);
        }

        this.viewPurchaseItemResponseList = purchase.getPurchaseItemList()
                .stream()
                .map(ViewPurchaseItemResponse::new)
                .collect(Collectors.toList());
    }
}
