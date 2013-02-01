package com.nayidisha.empdep.rest;

import lombok.Data;

import org.codehaus.jackson.annotate.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;

import com.nayidisha.empdep.domain.Department;

public @Data  class DepartmentResource extends ResourceSupport {

	@JsonUnwrapped
	public Department department;
}
