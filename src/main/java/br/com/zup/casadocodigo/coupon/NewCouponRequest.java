package br.com.zup.casadocodigo.coupon;

import br.com.zup.casadocodigo.validation.Unique;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class NewCouponRequest {

    @NotBlank
    @Unique(clazz = Coupon.class)
    private String code;

    @NotNull @Positive
    private BigDecimal percentageDiscount;

    @FutureOrPresent
    private LocalDate validUntil;

    public Coupon toModel() {

        return new Coupon(code, percentageDiscount, validUntil);
    }
}
