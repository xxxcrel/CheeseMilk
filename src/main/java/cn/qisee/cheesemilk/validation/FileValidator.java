package cn.qisee.cheesemilk.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    @Override
    public void initialize(ValidFile constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {

        boolean result = true;
        if(value == null || value.isEmpty())
            result = false;
        return result;
    }
}
