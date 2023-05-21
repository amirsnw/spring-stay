package com.stay.service;

import com.stay.domain.entity.RoomEntity;
import com.stay.resource.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public RoomEntity saveRoom(RoomEntity roomEntity) {
        return roomRepository.save(roomEntity);
    }

    public RoomEntity getRoom(int id) {
        return roomRepository.getById(id);
    }
}
