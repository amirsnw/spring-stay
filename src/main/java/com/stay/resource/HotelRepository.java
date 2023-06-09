package com.stay.resource;

import com.stay.domain.jpaEntity.HotelEntity;
import com.stay.exception.NotRecordFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Integer> {
    default HotelEntity getById(Integer id) {
        return findById(id).orElseThrow(NotRecordFoundException::new);
    }
}
