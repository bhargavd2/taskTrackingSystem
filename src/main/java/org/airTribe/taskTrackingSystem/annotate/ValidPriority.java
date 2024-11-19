package org.airTribe.taskTrackingSystem.annotate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.airTribe.taskTrackingSystem.annotate.validator.PriorityValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PriorityValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPriority {
    String message() default "Invalid priority.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}