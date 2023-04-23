package com.stay.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

//@ApiModel(description="All details about the hotel.")
@Entity
public class Hotel {

	@Id
	@GeneratedValue
	private Integer id;

	@Size(min=2, message="Name should have atleast 2 characters")
	//@ApiModelProperty(notes="Name should have atleast 2 characters")
	private String name;

	@Past
	//@ApiModelProperty(notes="Birth date should be in the past")
	private Date birthDate;
	
	@OneToMany(mappedBy="hotel")
	private List<Room> rooms;

	protected Hotel() {

	}

	public Hotel(Integer id, String name, Date birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	@Override
	public String toString() {
		return String.format("Hotel [id=%s, name=%s, birthDate=%s]", id, name, birthDate);
	}

}
