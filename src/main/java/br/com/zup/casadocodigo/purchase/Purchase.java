package br.com.zup.casadocodigo.purchase;

import br.com.zup.casadocodigo.country.Country;
import br.com.zup.casadocodigo.coupon.Coupon;
import br.com.zup.casadocodigo.purchase.item.PurchaseItem;
import br.com.zup.casadocodigo.state.State;
import br.com.zup.casadocodigo.validation.CpfCnpj;
import org.springframework.util.Assert;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    private String lastName;

    @NotBlank @CpfCnpj
    @Column(nullable = false)
    private String document;

    @NotBlank
    @Column(nullable = false)
    private String address;

    @NotBlank
    @Column(nullable = false)
    private String complement;

    @NotBlank
    @Column(nullable = false)
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    private State state;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Country country;

    @NotBlank
    @Column(nullable = false)
    private String phone;

    @NotBlank
    @Column(nullable = false)
    private String cep;

    @NotNull @Positive
    @Column(nullable = false)
    private BigDecimal total;

    @NotNull @Size(min = 1)
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.PERSIST)
    private List<PurchaseItem> purchaseItemList;

    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

    /**
     * Hibernate usage only
     */
    @Deprecated
    protected Purchase() { }

    public Purchase(String email,
                    String firstName,
                    String lastName,
                    String document,
                    String address,
                    String complement,
                    String city,
                    State state,
                    Country country,
                    String phone,
                    String cep,
                    BigDecimal total,
                    List<PurchaseItem> purchaseItemList) {

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.document = document;
        this.address = address;
        this.complement = complement;
        this.city = city;
        this.state = state;
        this.country = country;
        this.phone = phone;
        this.cep = cep;
        this.total = total;
        this.purchaseItemList = purchaseItemList;
        this.purchaseItemList.forEach(purchaseItem -> purchaseItem.setPurchase(this));

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Purchase>> violationSet = validator.validate(this);
        if (!violationSet.isEmpty()) throw new ConstraintViolationException(violationSet);
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getDocument() {
        return this.document;
    }

    public String getAddress() {
        return this.address;
    }

    public String getComplement() {
        return this.complement;
    }

    public String getCity() {
        return this.city;
    }

    public State getState() {
        return this.state;
    }

    public Country getCountry() {
        return this.country;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getCep() {
        return this.cep;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public Coupon getCoupon() {
        return this.coupon;
    }

    public void addPurchaseItem(PurchaseItem purchaseItem) {
        this.purchaseItemList.add(purchaseItem);
        purchaseItem.setPurchase(this);
    }

    public List<PurchaseItem> getPurchaseItemList() {
        return Collections.unmodifiableList(this.purchaseItemList);
    }

    public void setCoupon(Coupon coupon) {
        Assert.isNull(this.coupon, "Não é possível atualizar o Cupom!");
        Assert.isTrue(coupon.isValid(),"O cupom expirou");
        this.coupon = coupon;
    }
}
