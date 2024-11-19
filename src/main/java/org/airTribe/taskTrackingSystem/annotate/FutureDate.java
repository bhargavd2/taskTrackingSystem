package org.airTribe.taskTrackingSystem.annotate;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.airTribe.taskTrackingSystem.annotate.validator.DueDateValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DueDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureDate {
    String message() default "The due date must be a future date.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
