package com.stay.domain;

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
public class Hotel {

	@Id
	@GeneratedValue
	private Integer id;

	@Size(min = 2, message="Name should have atleast 2 characters")
	// @ApiModelProperty(notes="Name should have atleast 2 characters")
	private String name;

	// @ApiModelProperty(notes="Birth date should be in the past")
	private String address;
	
	@OneToMany(mappedBy = "hotel")
	private List<Room> rooms;
}
