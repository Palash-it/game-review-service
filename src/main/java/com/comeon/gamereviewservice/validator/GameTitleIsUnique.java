package com.comeon.gamereviewservice.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GameTitleIsUniqueValidator.class)
@Documented
public @interface GameTitleIsUnique {

    String message() default "{GameTitleIsUnique.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
