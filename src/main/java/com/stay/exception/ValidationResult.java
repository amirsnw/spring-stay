package com.stay.exception;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ValidationResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String model;
	private Set<String> objectViolations;
	private Map<String, Set<String>> propertyViolations;

	public ValidationResult() {
		super();
		objectViolations = new HashSet<>();
		propertyViolations = new HashMap<>();
	}

	public String getModel() {
		return model;
	}

	public void setModel(String fieldName) {
		this.model = fieldName;
	}

	public Set<String> getObjectViolations() {
		return objectViolations;
	}

	public void setObjectViolations(Set<String> objectViolations) {
		this.objectViolations = objectViolations;
	}

	public Map<String, Set<String>> getPropertyViolations() {
		return propertyViolations;
	}

	public void setPropertyViolations(Map<String, Set<String>> propertyViolations) {
		this.propertyViolations = propertyViolations;
	}
}
