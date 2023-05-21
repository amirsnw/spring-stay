package com.stay.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.List;

//@ApiModel(description="All details about the hotel.")
@Entity
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
public class HotelEntity {

	@Id
	@GeneratedValue
	private Integer id;

	@Size(min = 2, message="Name should have at least 2 characters")
	// @ApiModelProperty(notes="Name should have at least 2 characters")
	private String name;

	@Size(max = 500, message="Address should less than 500 character")
	// @ApiModelProperty(notes="Address should less than 500 character")
	private String address;
	
	@OneToMany(mappedBy = "hotelEntity")
	private List<RoomEntity> roomEntities;
}
