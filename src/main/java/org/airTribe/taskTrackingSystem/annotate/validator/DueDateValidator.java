package org.airTribe.taskTrackingSystem.annotate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.airTribe.taskTrackingSystem.annotate.FutureDate;

import java.time.LocalDate;

public class DueDateValidator implements ConstraintValidator<FutureDate, LocalDate> {

    @Override
    public void initialize(FutureDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate dueDate, ConstraintValidatorContext context) {
        if (dueDate == null) {
            return true; // assuming @NotEmpty takes care of non-null requirement
        }
        return dueDate.isAfter(LocalDate.now());
    }
}
