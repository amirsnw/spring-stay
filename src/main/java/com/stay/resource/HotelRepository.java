package com.stay.resource;

import com.stay.domain.Hotel;
import com.stay.domain.Room;
import com.stay.exception.NotRecordFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    default Hotel getById(Integer id) {
        return findById(id).orElseThrow(NotRecordFoundException::new);
    }
}
