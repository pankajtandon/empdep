package com.nayidisha.empdep.domain;

import lombok.Data;

import org.springframework.hateoas.Identifiable;

public @Data class Department implements Identifiable<Long> {

	public Department(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	private Long id;
	private String name;
	private Long managerId;

}
