package com.stay.domain.mongoDocument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

//@ApiModel(description="All details about the hotel.")
@Document
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PassengerDocument {

	@Id
	private Integer id;
	private String firstName;
	private String lastName;
	private String nationality;
}
