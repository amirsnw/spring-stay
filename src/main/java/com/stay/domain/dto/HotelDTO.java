package com.stay.domain.dto;

import lombok.*;

import java.util.List;

//@ApiModel(description="All details about the hotel.")
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
public class HotelDTO {

	private Integer id;
	private String name;
	private String address;
	private List<RoomDTO> rooms;
}
