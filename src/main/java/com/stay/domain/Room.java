package com.stay.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
public class Room {
	
	@Id
	@GeneratedValue
	private Integer id;

	private String number;

	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private Hotel hotel;

	public Room (String number) {
		this.number = number;
	}
}