package com.nayidisha.empdep.domain;

import lombok.Data;

import org.springframework.hateoas.Identifiable;

public @Data class Employee implements Identifiable<Long>{
	public Employee(Long id, String fName, String lName) {
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		
		this.status = EmployeeStatus.WORKING;
	}	
	private final Long id;
	private final String fName;
	private final String lName;
 	private double salary;
 	private Long depId;
 	private EmployeeStatus status;
}
