package br.com.zup.casadocodigo.validation;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.util.annotation.ConstraintAnnotationDescriptor;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

public class ExistentIdValidator implements ConstraintValidator<ExistentId, Long> {

    private final EntityManager entityManager;
    private String className;
    private String idFieldName;

    public ExistentIdValidator(EntityManager entityManager) {
        this.entityManager = entityManager; // treinar depois com JdbcTemplate
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        this.forceInitiliaze(context);

        if (id == null) return true;

        if (this.className.isEmpty()) {
            this.className = this.getClassNameWithReflection(context);
        }

        if (this.idFieldName.isEmpty()) {
            this.idFieldName = "id";
        }

        return !(this.entityManager
                .createQuery("select 1 from " + this.className + " where " + this.idFieldName + "= :id")
                .setParameter("id", id)
                .getResultList()
                .isEmpty());
    }

    private String getClassNameWithReflection(ConstraintValidatorContext context) {
        try {
            Field basePath = ConstraintValidatorContextImpl.class.getDeclaredField("basePath");
            basePath.setAccessible(true);
            PathImpl pathImpl = (PathImpl) basePath.get(context);
            String fieldName = pathImpl.getLeafNode().asString();
            return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length() - 2);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Até o momento (26/11/2020) foi verificado que a Bean Validation (ou a implementação da especificação usada) não
     * chama o método {@link #initialize(Annotation)} para cada inserção da anotação em alguma classe a ser validada.
     * <p>
     * Caso a declaração da anotação seja a mesma, o método de inicialização só chamado uma única vez. Por exemplo:
     *
     * <code>
     *
     * @ExistentId private Long stateId;
     *
     * @NotNull
     * @ExistentId private Long countryId;
     * </code>
     * <p>
     * Por conta desse comportamento, essa inicialização forçada foi implementada
     */
    private void forceInitiliaze(ConstraintValidatorContext context) {
        try {
            Field constraintDescriptorField = ConstraintValidatorContextImpl.class.getDeclaredField("constraintDescriptor");
            constraintDescriptorField.setAccessible(true);
            ConstraintDescriptorImpl constraintDescriptorImpl = (ConstraintDescriptorImpl) constraintDescriptorField.get(context);

            Field annotationDescriptorField = ConstraintDescriptorImpl.class.getDeclaredField("annotationDescriptor");
            annotationDescriptorField.setAccessible(true);
            ConstraintAnnotationDescriptor constraintAnnotationDescriptor = (ConstraintAnnotationDescriptor) annotationDescriptorField.get(constraintDescriptorImpl);

            Map attributes = constraintAnnotationDescriptor.getAttributes();
            this.className = (String) attributes.get("className");
            this.idFieldName = (String) attributes.get("field");

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

}
