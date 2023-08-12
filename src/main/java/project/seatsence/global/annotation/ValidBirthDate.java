package project.seatsence.global.annotation;


import project.seatsence.global.annotation.validator.BirthDateValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthDateValidator.class)
public @interface ValidBirthDate {
    String message() default "YYYY-MM-DD의 형식으로 입력해주세요.";

    Class[] groups() default {};

    Class[] payload() default {};
}
