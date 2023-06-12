package project.seatsence.global.annotation;

import project.seatsence.global.annotation.validator.EmailValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface ValidEmail {
    String message() default "이메일은 @까지 포함해 올바른 형식을 입력해주세요.";
    Class[] groups() default {};
    Class[] payload() default {};
}
