package project.seatsence.global.annotation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import project.seatsence.global.annotation.ValidNickname;

public class NicknameValidator implements ConstraintValidator<ValidNickname, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.matches("^[A-Za-z0-9ㄱ-ㅎ가-힣]{2,10}$");
    }
}
