package cn.qisee.cheesemilk.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {FileValidator.class})
public @interface ValidFile {

    String message() default "file can not be null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}