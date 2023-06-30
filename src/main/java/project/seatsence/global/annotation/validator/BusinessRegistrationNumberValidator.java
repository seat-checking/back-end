package project.seatsence.global.annotation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import project.seatsence.global.annotation.ValidBusinessRegistrationNumber;

public class BusinessRegistrationNumberValidator
        implements ConstraintValidator<ValidBusinessRegistrationNumber, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.matches("^[0-9]{10}$"); // XXXXXXXXXX
    }
}
