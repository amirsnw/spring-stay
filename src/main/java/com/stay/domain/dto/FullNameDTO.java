package com.stay.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@AllArgsConstructor
@ToString
public class FullNameDTO {
    String firstName;
    String lastName;

    public String getRawFullName() {
        return firstName + " " + lastName;
    }
}
