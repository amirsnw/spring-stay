package com.stay.service;

import com.stay.domain.entity.HotelEntity;
import com.stay.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Component
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<HotelEntity> getHotelList() {
        return hotelRepository.findAll();
    }

    public HotelEntity saveHotel(HotelEntity hotelEntity) {
        return hotelRepository.save(hotelEntity);
    }

    public HotelEntity getHotel(int id) {
        Optional<HotelEntity> hotelEntity = hotelRepository.findById(id);

        if (!hotelEntity.isPresent())
            throw new EntityNotFoundException("id-" + id);

        return hotelEntity.get();
    }

    public void deleteHotel(int id) {
        hotelRepository.deleteById(id);
    }
}
