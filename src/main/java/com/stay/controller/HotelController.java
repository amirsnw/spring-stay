package com.stay.controller;

import com.stay.domain.Hotel;
import com.stay.domain.Room;
import com.stay.service.HotelService;
import com.stay.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;

	@Autowired
	private RoomService roomService;

	@GetMapping
	public List<Hotel> retrieveAllHotels() {
		return hotelService.getHotelList();
	}

	@PostMapping
	public ResponseEntity<Object> createHotel(@Valid @RequestBody Hotel hotel) {
		Hotel savedHotel = hotelService.saveHotel(hotel);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedHotel.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{id}")
	public EntityModel<Hotel> retrieveHotel(@PathVariable int id) {
		Hotel hotel = hotelService.getHotel(id);

		// Create hateoas resource
		EntityModel<Hotel> resource = EntityModel.of(hotel); // new EntityModel<Hotel>(hotel.get());

		// Link to retrieveAllHotels resource
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllHotels());

		// Add "all-hotels" link to hateoas resource
		resource.add(linkTo.withRel("all-hotels"));

		return resource;
	}

	@DeleteMapping("/{id}")
	public void deleteHotel(@PathVariable int id) {
		hotelService.deleteHotel(id); // Returns 200
	}

	// Room Related Resources

	@PostMapping("/{id}/rooms")
	public ResponseEntity<Object> createRoom(@PathVariable int id, @RequestBody Room room) {

		Hotel hotel = hotelService.getHotel(id);

		// connect hotel to room
		room.setHotel(hotel);

		roomService.saveRoom(room);

		// Append id of created room to current URI
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(room.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{id}/rooms")
	public List<Room> retrieveAllHotelRooms(@PathVariable int id) {

		Hotel hotel = hotelService.getHotel(id);

		return hotel.getRooms();
	}
}
