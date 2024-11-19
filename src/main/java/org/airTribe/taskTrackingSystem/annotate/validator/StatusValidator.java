package org.airTribe.taskTrackingSystem.annotate.validator;

import org.airTribe.taskTrackingSystem.entity.Status;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.airTribe.taskTrackingSystem.annotate.ValidStatus;

public class StatusValidator implements ConstraintValidator<ValidStatus, String> {

    @Override
    public void initialize(ValidStatus constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return true;
        return isValidStatus(value);
    }

    public static boolean isValidStatus(String value) {
        for (Status status : Status.values())
        {
            if (status.name().equalsIgnoreCase(value)) return true;
        }
        return false;
    }
}
