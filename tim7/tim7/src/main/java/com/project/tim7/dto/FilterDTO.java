package com.project.tim7.dto;

public class FilterDTO {
	
	private String value;
	private String parameter;
	
	public FilterDTO() {
		super();
	}

	public FilterDTO(String value, String parameter) {
		super();
		this.value = value;
		this.parameter = parameter;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
}
