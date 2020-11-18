package br.com.zup.casadocodigo.author;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private  String name;

    @NotBlank @Email
    @Column(nullable = false, unique = true)
    private  String email;

    @NotBlank @Size(max = 400)
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false)
    private final LocalDateTime creationTime = LocalDateTime.now();

    /**
     * Hibernate usage only
     */
    @Deprecated
    public Author() { }

    public Author(@NotBlank String name,
                  @NotBlank @Email String email,
                  @NotBlank @Size(max = 400) String description) {
        this.name = name;
        this.email = email;
        this.description = description;
    }
}
