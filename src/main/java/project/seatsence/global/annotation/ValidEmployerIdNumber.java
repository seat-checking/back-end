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
    String message() default "";

    Class[] groups() default {};

    Class[] payload() default {};
}
