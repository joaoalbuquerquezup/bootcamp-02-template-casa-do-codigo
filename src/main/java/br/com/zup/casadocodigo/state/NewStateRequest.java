package br.com.zup.casadocodigo.state;

import br.com.zup.casadocodigo.country.Country;
import br.com.zup.casadocodigo.validation.Unique;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.function.LongFunction;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class NewStateRequest {

    @NotBlank
    @Unique(clazz = State.class)
    private String name;

    @NotNull
    private Long countryId;

    public State toModel(LongFunction<Country> countryLoader) {
        return new State(name, countryLoader.apply(this.countryId));
    }
}
