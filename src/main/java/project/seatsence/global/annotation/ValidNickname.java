package project.seatsence.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import project.seatsence.global.annotation.validator.NicknameValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NicknameValidator.class)
public @interface ValidNickname {
    String message() default "닉네임은 2~10자의 영문(대,소문자 구분)과 숫자만 허용합니다";

    Class[] groups() default {};

    Class[] payload() default {};
}
