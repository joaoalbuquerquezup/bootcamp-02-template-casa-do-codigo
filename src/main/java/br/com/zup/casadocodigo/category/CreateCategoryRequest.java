package br.com.zup.casadocodigo.category;

import br.com.zup.casadocodigo.validation.Unique;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CreateCategoryRequest {

    public @NotBlank @Unique(clazz = Category.class, field = "name") String name;

    public Category toModel() {
        return new Category(this.name);
    }
}
