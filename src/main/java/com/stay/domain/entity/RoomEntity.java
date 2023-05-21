package com.stay.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stay.util.RoomConstraint;
import lombok.*;

import javax.persistence.*;

@Entity
@RoomConstraint
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
public class RoomEntity {
	
	@Id
	@GeneratedValue
	private Integer id;

	private String number;

	@ManyToOne(fetch=FetchType.LAZY)
	// @JoinColumn(name="hotel-id")
	@JsonIgnore
	private HotelEntity hotelEntity;

	public RoomEntity(String number) {
		this.number = number;
	}
}
