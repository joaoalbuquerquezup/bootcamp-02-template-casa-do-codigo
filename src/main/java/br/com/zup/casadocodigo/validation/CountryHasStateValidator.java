package br.com.zup.casadocodigo.validation;

import br.com.zup.casadocodigo.country.Country;
import br.com.zup.casadocodigo.purchase.NewPurchaseRequest;
import br.com.zup.casadocodigo.state.State;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static java.util.Objects.nonNull;

/**
 * Apenas para praticar com Validator do Spring.
 * Porém acho o padrão da BeanValidation com uma experiência de desenvolvimento melhor
 */
@Component
public class CountryHasStateValidator implements Validator {

    private final EntityManager entityManager;

    public CountryHasStateValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewPurchaseRequest.class.isAssignableFrom(clazz);
    }

    @Override // Praticar com a validação usando a especificação da BeanValidation
    @Transactional
    public void validate(Object target, Errors errors) {
        NewPurchaseRequest newPurchaseRequest = (NewPurchaseRequest) target;

        Long countryId = newPurchaseRequest.getCountryId();
        Long stateId = newPurchaseRequest.getStateId();

        // Escreveria menos aqui Talvez aqui seja melhor fazer um bidirecional? Teria que fazer um join fetch pra evitar n+1
        // Adc queryUtils e Hypersistence de Vlad Mihalcea
        int existentStates = this.entityManager
                .createNativeQuery("SELECT 1 FROM state s WHERE s.country_id = :countryId")
                .setParameter("countryId", countryId)
                .getResultList()
                .size();

        // se eu pegar a lista dos estados aqui acima, ao invés do tamanho da lista, evito uma ida no banco abaixo (no segundo if)
        // Pq checo se o meu estado está nessa lista

        if (stateId == null && existentStates > 0) {
            errors.rejectValue("stateId", "", "O estado não pode ser nulo se o país associado tiver estados disponíveis");
        }

        if (stateId != null) {
            Country country = this.entityManager.find(Country.class, countryId);
            State state = this.entityManager.find(State.class, stateId);

            if (nonNull(state) && nonNull(country) && !(state.belongsTo(country))) {
                errors.rejectValue("stateId", "", "Esse estado não pertence a esse país");
            }

        }
    }
}
