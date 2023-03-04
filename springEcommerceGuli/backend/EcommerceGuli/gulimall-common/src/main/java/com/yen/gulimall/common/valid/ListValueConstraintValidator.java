package com.yen.gulimall.common.valid;

// https://youtu.be/r8naBc3IBNE?t=385

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {

    private Set<Integer> set = new HashSet<>();

    // init method
    @Override
    public void initialize(ListValue constraintAnnotation) {

        int[] vals = constraintAnnotation.vals();
        for (int val: vals){
            set.add(val);
        }
        //ConstraintValidator.super.initialize(constraintAnnotation);
    }

    // check if validate successfully
    // value : the value need to be validated
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        return set.contains(value);
    }

}
