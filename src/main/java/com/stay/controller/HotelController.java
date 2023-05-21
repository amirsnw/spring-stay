package com.stay.controller;

import com.stay.domain.entity.HotelEntity;
import com.stay.domain.entity.RoomEntity;
import com.stay.resource.cache.BaseCache;
import com.stay.service.HotelService;
import com.stay.service.RoomService;
import com.stay.util.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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

	@Autowired
	@Qualifier("room-cache")
	private BaseCache cacheService;

	@Autowired
	private Statistic statistic;

	@GetMapping
	public List<HotelEntity> retrieveAllHotels() {
		cacheService.getAll();
		return hotelService.getHotelList();
	}

	@PostMapping
	public ResponseEntity<Object> createHotel(@Valid @RequestBody HotelEntity hotelEntity) {
		HotelEntity savedHotel = hotelService.saveHotel(hotelEntity);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedHotel.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{id}")
	public EntityModel<HotelEntity> retrieveHotel(@PathVariable int id) {

		HotelEntity hotelEntity = hotelService.getHotel(id);

		// Create hateoas resource
		EntityModel<HotelEntity> resource = EntityModel.of(hotelEntity); // new EntityModel<Hotel>(hotelEntity.get());

		// Link to retrieveAllHotels resource
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllHotels());

		// Add "all-hotels" link to hateoas resource
		resource.add(linkTo.withRel("all-hotels"));
		resource.add(new Link(String.valueOf(statistic.getNextValue()), "total-visit"));

		return resource;
	}

	@DeleteMapping("/{id}")
	public void deleteHotel(@PathVariable int id) {
		hotelService.deleteHotel(id); // Returns 200
	}

	// Room Related Resources

	@PostMapping("/{id}/rooms")
	public ResponseEntity<Object> createRoom(@PathVariable int id, @RequestBody RoomEntity roomEntity) {

		HotelEntity hotelEntity = hotelService.getHotel(id);

		// connect hotelEntity to roomEntity
		roomEntity.setHotelEntity(hotelEntity);

		roomService.saveRoom(roomEntity);

		// Append id of created roomEntity to current URI
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(roomEntity.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{id}/rooms")
	public List<RoomEntity> retrieveAllHotelRooms(@PathVariable int id) {

		HotelEntity hotelEntity = hotelService.getHotel(id);

		return hotelEntity.getRoomEntities();
	}
}
