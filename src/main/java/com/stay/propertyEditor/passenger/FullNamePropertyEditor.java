package com.stay.propertyEditor.passenger;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FullNamePropertyEditor extends PropertyEditorSupport {

    // Is used to convert a String to another object.
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        List<Integer> parameters = extractConfig(text);
        FullNameModel fullName = new FullNameModel(parameters.get(0), parameters.get(1));
        setValue(fullName);
    }

    // Is called when serializing an object to a String
    @Override
    public String getAsText() {
        FullNameModel fullName = (FullNameModel) getValue();

        return fullName == null ? "" : fullName.getRawFullName();
    }

    private List<Integer> extractConfig(String text) {
        List<Integer> parameters;
        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException("Empty FullName!");
        }

        try {
            parameters = Arrays
                    .stream(text.split(" "))
                    .map(item -> Integer.parseInt(text))
                    .collect(Collectors.toList());
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(nfe);
        }

        if (parameters.size() != 2) {
            throw new IllegalArgumentException(
                    "Cache config should be xxx-xxx-xxx");
        }

        return parameters;
    }
}
