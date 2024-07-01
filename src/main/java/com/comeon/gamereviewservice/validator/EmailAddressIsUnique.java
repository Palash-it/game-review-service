package com.comeon.gamereviewservice.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailAddressIsUniqueValidator.class)
@Documented
public @interface EmailAddressIsUnique {

    String message() default "{EmailAddressIsUnique.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
