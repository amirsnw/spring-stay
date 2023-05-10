package com.stay.service;

import com.stay.domain.Room;
import com.stay.resource.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room getRoom(int id) {
        return roomRepository.getById(id);
    }
}
