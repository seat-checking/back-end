package project.seatsence.global.annotation;

import project.seatsence.global.annotation.validator.NicknameValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NicknameValidator.class)
public @interface ValidNickname {
    String message() default "닉네임은 4~12자의 영문(대,소문자 구분)과 숫자만 허용합니다";

    Class[] groups() default {};

    Class[] payload() default {};
}
