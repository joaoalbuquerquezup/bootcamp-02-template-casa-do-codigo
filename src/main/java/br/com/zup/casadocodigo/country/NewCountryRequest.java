package br.com.zup.casadocodigo.country;

import br.com.zup.casadocodigo.validation.Unique;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class NewCountryRequest {

    @NotBlank
    @Unique(clazz = Country.class)
    private String name;

    public Country toModel() {
        return new Country(this.name);
    }
}
