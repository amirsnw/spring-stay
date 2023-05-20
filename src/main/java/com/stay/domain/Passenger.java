package com.stay.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stay.propertyEditor.passenger.FullNameModel;
import com.stay.util.RoomConstraint;
import lombok.*;

import javax.persistence.*;

@Entity
@RoomConstraint
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
public class Passenger {

	@Id
	@GeneratedValue
	private Integer id;

	@Transient
	private FullNameModel fullName;

	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private Hotel hotel;
}
