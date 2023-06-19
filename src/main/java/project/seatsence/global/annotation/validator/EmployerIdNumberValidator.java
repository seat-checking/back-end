package project.seatsence.global.annotation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import project.seatsence.global.annotation.ValidEmployerIdNumber;

public class EmployerIdNumberValidator
        implements ConstraintValidator<ValidEmployerIdNumber, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.matches("^[0-9]{10}$"); // XXXXXXXXXXXXX
    }
}
