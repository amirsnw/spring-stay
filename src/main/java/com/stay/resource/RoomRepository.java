package com.stay.resource;

import com.stay.domain.jpaEntity.RoomEntity;
import com.stay.exception.NotRecordFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {
    default RoomEntity getById(Integer id) {
        return findById(id).orElseThrow(NotRecordFoundException::new);
    }
    RoomEntity findByNumber(String number);
}
