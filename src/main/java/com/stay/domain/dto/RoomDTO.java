package com.stay.domain.dto;

import com.stay.util.RoomConstraint;
import lombok.*;

@RoomConstraint
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
public class RoomDTO {

	private Integer id;
	private String number;
	private HotelDTO hotel;
}
