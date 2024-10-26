package org.airTribe.taskTrackingSystem.annotate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.airTribe.taskTrackingSystem.annotate.ValidPriority;
import org.airTribe.taskTrackingSystem.entity.Priority;

public class PriorityValidator implements ConstraintValidator<ValidPriority, String> {

    @Override
    public void initialize(ValidPriority constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return true;
        return isValidPriority(value);
    }

    public static boolean isValidPriority(String value) {
        for (Priority priority : Priority.values()) {
            if (priority.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
