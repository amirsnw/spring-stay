package com.stay.propertyEditor.passenger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@AllArgsConstructor
@ToString
public class FullNameModel {
    long firstName;
    long lastName;

    public String getRawFullName() {
        return firstName + " " + lastName;
    }
}
