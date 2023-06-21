package project.seatsence.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import project.seatsence.global.annotation.validator.CategoryValidator;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CategoryValidator.class)
public @interface ValidCategory {

    String message() default "올바른 카테고리를 선택해주세요.";

    Class[] groups() default {};

    Class[] payload() default {};
}
