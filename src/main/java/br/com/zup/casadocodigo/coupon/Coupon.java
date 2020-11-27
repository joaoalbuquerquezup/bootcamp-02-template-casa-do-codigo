package br.com.zup.casadocodigo.coupon;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false, updatable = false)
    private String code;

    @NotNull @Positive
    @Column(nullable = false, updatable = false)
    private BigDecimal percentageDiscount;

    @FutureOrPresent
    @Column(nullable = false, updatable = false)
    private LocalDate validUntil;

    /**
     * Hibernate usage only
     */
    @Deprecated
    protected Coupon() { }

    public Coupon(@NotBlank String code,
                  @NotNull @Positive BigDecimal percentageDiscount,
                  @Future LocalDate validUntil) {
        this.code = code;
        this.percentageDiscount = percentageDiscount;
        this.validUntil = validUntil;
    }

    public BigDecimal getPercentageDiscount() {
        return percentageDiscount;
    }

    public boolean isValid() {
        return LocalDate.now().compareTo(this.validUntil) <= 0;
    }
}
