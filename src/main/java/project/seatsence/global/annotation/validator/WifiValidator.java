package project.seatsence.global.annotation.validator;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import project.seatsence.global.annotation.ValidWifi;

public class WifiValidator implements ConstraintValidator<ValidWifi, List<String>> {

    @Override
    public void initialize(ValidWifi constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        return value != null && !value.isEmpty();
    }
}
