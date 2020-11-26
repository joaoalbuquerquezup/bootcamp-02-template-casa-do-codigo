package br.com.zup.casadocodigo.validation;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {

    public static final CPFValidator cpfValidator = new CPFValidator();
    public static final CNPJValidator cnpjValidator = new CNPJValidator();

    /**
     * Calma coleguinha, não me xingue!
     *
     * Entendo que aqui há uma quebra de pilar do nosso desenvolvimento: Passagem de referência nula
     *
     * Contudo, como estamos altamente acoplados com o ecossistema do Hibernate e a fim de já aproveitar sua boa
     * implementação de validação desses documentos, entendo que esse código atende bem a necessidade da funcionalidade.
     * Além disso, foi checado que o parâmetro passado nem é usado. Portanto, no momento, não há nenhum problema
     */
    static {
        cpfValidator.initialize(null);
        cnpjValidator.initialize(null);
    }

    public boolean isValid(String cpfOrCnpj, ConstraintValidatorContext context) {
        if (cpfOrCnpj == null) return true;

        return cnpjValidator.isValid(cpfOrCnpj, context) || cpfValidator.isValid(cpfOrCnpj, context);
    }
}
