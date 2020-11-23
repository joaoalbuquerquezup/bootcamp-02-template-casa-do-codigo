package br.com.zup.casadocodigo.validation;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

@Component
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private final EntityManager entityManager;
    private Class<?> entityClass;
    private String entityField;

    public UniqueValidator (EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.entityClass = constraintAnnotation.clazz();
        this.entityField = constraintAnnotation.field();
    }

    @Override
    @Transactional
    public boolean isValid (Object propValueThatMustBeUnique, ConstraintValidatorContext context) {

        if (this.entityField.isEmpty()) {
            this.entityField = getFieldValueByReflection(context);
        }

        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();

        Root<?> from = criteriaQuery.from(this.entityClass);
        Path<Object> columnThatMustBeUnique = from.get(this.entityField);
        Predicate valueMustBeUnique = criteriaBuilder.equal(columnThatMustBeUnique, propValueThatMustBeUnique);

        CriteriaQuery<Object> queryWithWhere = criteriaQuery.select(from).where(valueMustBeUnique);

        try {
            this.entityManager.createQuery(queryWithWhere).setMaxResults(1).getSingleResult();
            return false;
        } catch (NoResultException e) {
            return true;
        }
    }

    private String getFieldValueByReflection(ConstraintValidatorContext context) {
        try {
            Field basePath = ConstraintValidatorContextImpl.class.getDeclaredField("basePath");
            basePath.setAccessible(true);
            PathImpl pathImpl = (PathImpl) basePath.get(context);
            return pathImpl.getLeafNode().asString();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }
}
