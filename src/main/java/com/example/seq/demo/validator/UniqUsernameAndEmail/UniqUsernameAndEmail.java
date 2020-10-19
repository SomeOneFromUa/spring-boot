package com.example.seq.demo.validator.UniqUsernameAndEmail;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UniqUsernameAndEmailValidator.class)
public @interface UniqUsernameAndEmail {
    String message() default "not unique input data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
