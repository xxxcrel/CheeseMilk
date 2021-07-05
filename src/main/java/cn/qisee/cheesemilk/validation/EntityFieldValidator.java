package cn.qisee.cheesemilk.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class EntityFieldValidator implements ConstraintValidator<ValidEntityField, String> {

    Class<?> entityClass;
    @Override
    public void initialize(ValidEntityField constraintAnnotation) {
        entityClass = constraintAnnotation.entityClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = false;
        try{
            Field field = entityClass.getDeclaredField(value);
            result = true;
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        }

        return result;
    }
}
