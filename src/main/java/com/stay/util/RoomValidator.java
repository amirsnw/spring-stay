package com.stay.util;

import com.stay.domain.entity.RoomEntity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class RoomValidator implements ConstraintValidator<RoomConstraint, RoomEntity> {

    @Override
    public void initialize(RoomConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RoomEntity roomEntity, ConstraintValidatorContext context) {
        List<RoomEntity> rooms = roomEntity.getHotelEntity().getRoomEntities();
        for (RoomEntity hotelRoom : rooms) {
            if (roomEntity.getNumber().equals(hotelRoom.getNumber())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("The value 'number'" +
                        " is duplicate").addPropertyNode("number").addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
