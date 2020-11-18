package br.com.zup.casadocodigo.author;

import br.com.zup.casadocodigo.validation.Unique;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect (fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CreateAuthorRequest {

    private @NotBlank String name;
    private @NotBlank @Email @Unique(clazz = Author.class, field = "email") String email;
    private @NotBlank @Size(max = 400) String description;

    public Author toModel() {
        return new Author(this.name, this.email, this.description);
    }

}
