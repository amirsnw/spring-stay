package com.stay.controller;

import com.stay.domain.Hotel;
import com.stay.domain.Room;
import com.stay.resource.cache.BaseCache;
import com.stay.resource.cache.CacheFactory;
import com.stay.service.HotelService;
import com.stay.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping(path = "/{roomId}")
    public String reserveRoom(@PathVariable int roomId) {

        Room room = roomService.getRoom(roomId);

        // Add room to cache (reserve)
        cacheService.put(roomId, room);

        return messageSource.getMessage("welcome.home.message", new String[]{room.getNumber()},
                LocaleContextHolder.getLocale());
    }

    @DeleteMapping(path = "/{roomId}")
    public String releaseRoom(@PathVariable Integer roomId) {

        // Remove room to cache (reserve)
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
    public List<Room> getReservedRooms(@PathVariable Integer hotelId) {

        Hotel hotel = hotelService.getHotel(hotelId);

        List<Room> rooms = cacheService.getAll();

        return rooms.stream().filter(item -> item.getHotel().getId() == hotel.getId()).collect(Collectors.toList());
    }

    // Using Locale as @RequestHeader argument
	/*public String enterRoomAgain(@RequestHeader("Accept-Language") Locale locale) {
		return messageSource.getMessage("welcome.home.message", null, locale);
	}*/
}
