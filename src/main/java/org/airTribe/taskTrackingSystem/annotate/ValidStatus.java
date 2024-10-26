package org.airTribe.taskTrackingSystem.annotate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.airTribe.taskTrackingSystem.annotate.validator.StatusValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StatusValidator.class)
@Target({ElementType.TYPE_USE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStatus {
    String message() default "Invalid status.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
