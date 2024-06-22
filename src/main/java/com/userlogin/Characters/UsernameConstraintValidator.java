package com.userlogin.Characters;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameConstraintValidator implements ConstraintValidator<NoSpecialCharacters, String> {

    @Override
    public void initialize(NoSpecialCharacters constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Define your validation logic here using regular expressions
    	
    	if (value == null || value.length() < 5 || value.length() > 15) {
            return false;
        }
    	
        return value != null && !value.matches(".*[^a-zA-Z0-9].*");
    }
}
