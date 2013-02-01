package com.nayidisha.empdep.rest;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

import org.codehaus.jackson.annotate.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;

import com.nayidisha.empdep.domain.Employee;
import com.nayidisha.empdep.domain.EmployeeStatus;


public @Data class EmployeeResource extends ResourceSupport{
	
//	public  String fName;
//	public  String lName;
//	public double salary;
//	public EmployeeStatus status;

	@JsonUnwrapped
	public Employee employee;
}
