package com.stay.propertyEditor;

import com.stay.domain.dto.FullNameDTO;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FullNamePropertyEditor extends PropertyEditorSupport {

    // Is used to convert a String to another object.
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] first_last_name = extractConfig(text);
        FullNameDTO fullName = new FullNameDTO(first_last_name[0], first_last_name[1]);
        setValue(fullName);
    }

    // Is called when serializing an object to a String
    @Override
    public String getAsText() {
        FullNameDTO fullName = (FullNameDTO) getValue();

        return fullName == null ? "" : fullName.getRawFullName();
    }

    private String[] extractConfig(String text) {
        List<Integer> parameters;
        String[] fullName;
        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException("Empty FullName!");
        }

        fullName = text.split("-");
        if (fullName.length < 2) {
            throw new IllegalArgumentException(
                    "Full-name should be xxx-xxx");
        }

        return fullName;
    }
}
