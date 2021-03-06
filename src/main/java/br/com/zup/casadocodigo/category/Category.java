package br.com.zup.casadocodigo.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Hibernate usage only
     */
    @Deprecated
    protected Category() { }

    public Category(@NotBlank String name) {
        this.name = name;
    }
}
