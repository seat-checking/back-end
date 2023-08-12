package project.seatsence.global.annotation.validator;

import project.seatsence.global.annotation.ValidBirthDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BirthDateValidator implements ConstraintValidator<ValidBirthDate, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.matches("^(?:(?:19|20)\\d\\d)-(?:0[1-9]|1[0-2])-(?:0[1-9]|1\\d|2[0-9]|3[01])$"); //YYYY-MM-DD
    }
}
