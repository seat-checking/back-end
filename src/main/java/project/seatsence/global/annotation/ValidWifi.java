package project.seatsence.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import project.seatsence.global.annotation.validator.WifiValidator;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WifiValidator.class)
public @interface ValidWifi {

    String message() default "하나 이상의 가게 wifi 정보를 입력해주세요.";

    Class[] groups() default {};

    Class[] payload() default {};
}
