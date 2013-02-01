package com.nayidisha.empdep.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.nayidisha.empdep.domain.Employee;
import com.nayidisha.empdep.web.EmployeeController;

@Component
public class EmployeeResourceAssembler extends ResourceAssemblerSupport<Employee, EmployeeResource> {

	public EmployeeResourceAssembler() {
		super(EmployeeController.class, EmployeeResource.class);
	}

	public EmployeeResource toResource(Employee employee) {
		EmployeeResource resource = createResource(employee);
		//resource.status = employee.getStatus();
		resource.employee = employee;
		resource.add(linkTo(EmployeeController.class).slash(employee.getId()).withRel("employees"));
		return resource;
	}
}
