package com.stay.propertyEditor.cache;

import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CachePropertyEditor extends PropertiesEditor {

    // Is used to convert a String to another object.
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        List<Integer> parameters = extractConfig(text);
        CacheConfigModel cacheConfig = new CacheConfigModel(parameters.get(0), parameters.get(1), parameters.get(2));
        setValue(cacheConfig);
    }

    // Is called when serializing an object to a String
    @Override
    public String getAsText() {
        CacheConfigModel cacheConfig = (CacheConfigModel) getValue();

        return cacheConfig == null ? "" : cacheConfig.getRawCacheConfig();
    }

    private List<Integer> extractConfig(String text) {
        List<Integer> parameters;
        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException("Empty Config!");
        }

        try {
            parameters = Arrays
                    .stream(text.split("-"))
                    .map(item -> Integer.parseInt(text))
                    .collect(Collectors.toList());
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(nfe);
        }

        if (parameters.size() != 3) {
            throw new IllegalArgumentException(
                    "Cache config should be xxx-xxx-xxx");
        }

        return parameters;
    }
}
