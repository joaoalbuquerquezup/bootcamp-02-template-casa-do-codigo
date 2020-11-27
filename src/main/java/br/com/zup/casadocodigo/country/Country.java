package br.com.zup.casadocodigo.country;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return this.name.equals(country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
