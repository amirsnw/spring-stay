package com.stay.util;

import com.stay.domain.jpaEntity.RoomEntity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

// Pure Java (can not inject spring beans): implements ConstraintValidator<RoomConstraint, Room>
// Test org.springframework.validation.Validator
public class RoomValidator implements ConstraintValidator<RoomConstraint, RoomEntity> {

    /*@Override
    @SuppressWarnings("unchecked")
    public void validate(Object obj, Errors errors) {
        if (obj instanceof Room) {
            Room room = (Room) obj;
            List<Room> rooms = room.getHotel().getRooms();
            for (Room hotelRoom : rooms) {
                if (roomCache.get(hotelRoom.getId()) != null) {
                    errors.rejectValue("room", "hotel.room.reserved",
                            "Can not add new room to hotel while has a room reserved");
                }
            }
        }
    }*/

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

        return true; // Default
    }
}
