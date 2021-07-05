package cn.qisee.cheesemilk.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {EntityFieldValidator.class})
public @interface ValidEntityField {

    Class<?> entityClass();

    String message() default "entity no such field";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
