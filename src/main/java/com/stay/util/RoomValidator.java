package com.stay.util;

import com.stay.domain.Room;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

// Pure Java (can not inject spring beans): implements ConstraintValidator<RoomConstraint, Room>
// Test org.springframework.validation.Validator
public class RoomValidator implements ConstraintValidator<RoomConstraint, Room> {

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
    public boolean isValid(Room room, ConstraintValidatorContext context) {
        List<Room> rooms = room.getHotel().getRooms();
        for (Room hotelRoom : rooms) {
            if (room.getNumber().equals(hotelRoom.getNumber())) {
                return false;
            }
        }

        return true; // Default
    }
}
