package project.seatsence.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import project.seatsence.global.annotation.validator.EmployerIdNumberValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmployerIdNumberValidator.class)
public @interface ValidEmployerIdNumber {
    String message() default "-을 제외한 10자리의 사업자등록번호를 입력해주세요.";

    Class[] groups() default {};

    Class[] payload() default {};
}
