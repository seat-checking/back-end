package project.seatsence.global.annotation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import project.seatsence.global.annotation.ValidCategory;
import project.seatsence.src.store.domain.Category;

public class CategoryValidator implements ConstraintValidator<ValidCategory, String> {
    @Override
    public void initialize(ValidCategory constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Category[] enumConstants = Category.class.getEnumConstants();
        for (Category enumConstant : enumConstants) {
            if (enumConstant.getValue().equalsIgnoreCase(value)) return true;
        }
        return false;
    }
}
