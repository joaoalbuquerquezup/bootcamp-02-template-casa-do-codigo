package br.com.zup.casadocodigo.country;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String name;

    /**
     * Hibernate usage only
     */
    @Deprecated
    protected Country() { }

    public Country(@NotNull String name) {
        this.name = name;
    }

    public Object getId() {
        return this.id;
    }
}
