package br.com.zup.casadocodigo.state;

import br.com.zup.casadocodigo.country.Country;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Country country;

    /**
     * Hibernate usage only
     */
    @Deprecated
    protected State() { }

    public State(@NotNull String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public Long getId() {
        return this.id;
    }
}
