package br.com.zup.casadocodigo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {ExistentIdValidator.class})
@Target({ FIELD})
@Retention(RUNTIME)
public @interface ExistentId {

    String message() default "{existentId}";

    /**
     * (Optional) By reflection, the validator will get your {@link javax.persistence.Entity} by your field name
     * But if you need to change it for some reason, override using this field
     *
     * Example:
     *
     * With this field:
     * <code>private Long countryId;</code>
     *
     * the query will be done with "Country". The validator will capitalize the first char and remove the last two
     */
    String className() default "";

    /**
     * (Optional) If your id field in your {@link javax.persistence.Entity} has a different name than "id",
     *  change it here
     */
    String field() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}