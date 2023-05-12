package com.stay.resource;

import com.stay.domain.Room;
import com.stay.exception.NotRecordFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    default Room getById(Integer id) {
        return findById(id).orElseThrow(NotRecordFoundException::new);
    }
    Room findByNumber(String number);
}
