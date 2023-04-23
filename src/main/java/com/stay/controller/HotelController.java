package com.stay.controller;

import com.stay.exception.HotelNotFoundException;
import com.stay.model.Hotel;
import com.stay.model.Room;
import com.stay.resource.HotelRepository;
import com.stay.resource.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class HotelController {

	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private RoomRepository roomRepository;

	@GetMapping("/jpa/hotels")
	public List<Hotel> retrieveAllHotels() {
		return hotelRepository.findAll();
	}

	@GetMapping("/jpa/hotels/{id}")
	public EntityModel<Hotel> retrieveHotel(@PathVariable int id) {
		Optional<Hotel> hotel = hotelRepository.findById(id);

		if (!hotel.isPresent())
			throw new HotelNotFoundException("id-" + id);

		// "all-hotels", SERVER_PATH + "/hotels"
		// retrieveAllHotels
		EntityModel<Hotel> resource = EntityModel.of(hotel.get());//new EntityModel<Hotel>(hotel.get());

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllHotels());

		resource.add(linkTo.withRel("all-hotels"));

		// HATEOAS

		return resource;
	}

	@DeleteMapping("/jpa/hotels/{id}")
	public void deleteHotel(@PathVariable int id) {
		hotelRepository.deleteById(id);
	}

	//
	// input - details of hotel
	// output - CREATED & Return the created URI

	// HATEOAS

	@PostMapping("/jpa/hotels")
	public ResponseEntity<Object> createHotel(@Valid @RequestBody Hotel hotel) {
		Hotel savedHotel = hotelRepository.save(hotel);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedHotel.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}
	
	@GetMapping("/jpa/hotels/{id}/rooms")
	public List<Room> retrieveAllHotels(@PathVariable int id) {
		Optional<Hotel> hotelOptional = hotelRepository.findById(id);
		
		if(!hotelOptional.isPresent()) {
			throw new HotelNotFoundException("id-" + id);
		}
		
		return hotelOptional.get().getRooms();
	}


	@PostMapping("/jpa/hotels/{id}/rooms")
	public ResponseEntity<Object> createRoom(@PathVariable int id, @RequestBody Room room) {
		
		Optional<Hotel> hotelOptional = hotelRepository.findById(id);
		
		if(!hotelOptional.isPresent()) {
			throw new HotelNotFoundException("id-" + id);
		}

		Hotel hotel = hotelOptional.get();
		
		room.setHotel(hotel);
		
		roomRepository.save(room);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(room.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

}
