package com.stay.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoomValidator.class)
public @interface RoomConstraint {
    String message() default "Invalid Operation";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
