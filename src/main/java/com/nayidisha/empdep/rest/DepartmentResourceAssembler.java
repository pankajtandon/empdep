package com.nayidisha.empdep.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.nayidisha.empdep.domain.Department;
import com.nayidisha.empdep.web.DepartmentController;


@Component
public class DepartmentResourceAssembler extends ResourceAssemblerSupport <Department, DepartmentResource> {

	public DepartmentResourceAssembler() {
		super(DepartmentController.class, DepartmentResource.class);
	}

	public DepartmentResource toResource(Department department) {
		DepartmentResource resource = createResource(department);
		resource.department = department;
		resource.add(linkTo(DepartmentController.class).slash(department.getId()).withRel("departments"));
		return resource;
	}

}
