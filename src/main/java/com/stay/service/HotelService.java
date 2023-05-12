package com.stay.service;

import com.stay.domain.Hotel;
import com.stay.resource.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Component
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getHotelList() {
        return hotelRepository.findAll();
    }

    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel getHotel(int id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);

        if (!hotel.isPresent())
            throw new EntityNotFoundException("id-" + id);

        return hotel.get();
    }

    public void deleteHotel(int id) {
        hotelRepository.deleteById(id);
    }
}
