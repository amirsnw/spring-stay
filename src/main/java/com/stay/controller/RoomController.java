package com.stay.controller;

import com.stay.model.Room;
import com.stay.resource.cache.RoomCache;
import com.stay.util.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/virtual-hotel")
public class RoomController {

	@Autowired
	private RoomCache cacheService;

	@Autowired
	private MessageSource messageSource; 

	@GetMapping(path = "/rooms")
	public Room getAllRooms() {
		return new Room(Generator.getStringRandomNumber(1,50)); // Returns status-code 200
	}

	@GetMapping(path = "/rooms/{id}")
	public Room getSingleRoom(@PathVariable Integer id) {
		return cacheService.get(id); // Returns status-code 200
	}

	@GetMapping(path = "/enter-room")
	public String enterRoom() {
		return messageSource.getMessage("welcome.home.message", null,
									LocaleContextHolder.getLocale());
	}

	// Using Locale as @RequestHeader argument
	/*public String enterRoomAgain(@RequestHeader("Accept-Language") Locale locale) {
		return messageSource.getMessage("welcome.home.message", null, locale);
	}*/
}
