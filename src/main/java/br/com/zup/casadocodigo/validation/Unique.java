package br.com.zup.casadocodigo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
public @interface Unique {

    String message() default "{unique}";

    /**
     * The class type of a {@link javax.persistence.Entity}
     * that uniqueness will be checked
     */
    Class<?> clazz();

    /**
     * (Optional) the name of the field that will be
     * used in the search for uniqueness
     */
    String field() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}