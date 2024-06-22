package com.userlogin.Characters;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Size;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameConstraintValidator.class)
@Size(min = 5, max = 15, message = "Username length must be between 5 and 15 characters")
public @interface NoSpecialCharacters {

    String message() default "Username cannot contain special characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
