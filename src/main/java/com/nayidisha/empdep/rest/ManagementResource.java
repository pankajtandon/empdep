package com.nayidisha.empdep.rest;

import java.util.List;

import lombok.Data;

import org.springframework.hateoas.ResourceSupport;

import com.nayidisha.empdep.domain.Department;
import com.nayidisha.empdep.domain.Employee;

public @Data class ManagementResource extends ResourceSupport{
	
	public List<Employee> employeeList;
	public List<Department> departmentList;

}
