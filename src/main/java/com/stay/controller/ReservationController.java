package com.stay.controller;

import com.stay.domain.entity.HotelEntity;
import com.stay.domain.entity.RoomEntity;
import com.stay.domain.dto.FullNameDTO;
import com.stay.resource.cache.BaseCache;
import com.stay.service.HotelService;
import com.stay.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    RoomService roomService;

    @Autowired
    @Qualifier("room-cache")
    private BaseCache cacheService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private MessageSource messageSource;

    @PutMapping(path = "/{roomId}/{full-name}")
    public String reserveRoom(@PathVariable int roomId, @PathVariable("full-name") FullNameDTO fullNameDTO) {

        RoomEntity roomEntity = roomService.getRoom(roomId);

        // Add roomEntity to cache (reserve)
        cacheService.put(roomId, roomEntity);

        return messageSource.getMessage("welcome.home.message", new String[]{},
                LocaleContextHolder.getLocale()) + " " + fullNameDTO.getRawFullName();
    }

    @DeleteMapping(path = "/{roomId}")
    public String releaseRoom(@PathVariable Integer roomId) {

        // Remove roomEntity from cache (reserve)
        cacheService.remove(roomId);

        return messageSource.getMessage("goodbye.home.message", null,
                LocaleContextHolder.getLocale());
    }

    @GetMapping(path = "/{roomId}")
    public String checkRoomAvailability(@PathVariable Integer roomId) {
        String message = "";
        roomService.getRoom(roomId);
        if (cacheService.get(roomId) == null) {
            message = "room.available";
        } else {
            message = "room.not.available";
        }
        return messageSource.getMessage(message, null,
                LocaleContextHolder.getLocale());
    }

    @GetMapping(path = "hotels/{hotelId}")
    public List<RoomEntity> getReservedRooms(@PathVariable Integer hotelId) {

        HotelEntity hotelEntity = hotelService.getHotel(hotelId);

        List<RoomEntity> roomEntities = cacheService.getAll();

        return roomEntities.stream().filter(item -> item.getHotelEntity().getId() == hotelEntity.getId()).collect(Collectors.toList());
    }

    // Using Locale as @RequestHeader argument
	/*public String enterRoomAgain(@RequestHeader("Accept-Language") Locale locale) {
		return messageSource.getMessage("welcome.home.message", null, locale);
	}*/
}
