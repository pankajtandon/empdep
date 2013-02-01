package com.nayidisha.empdep.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nayidisha.empdep.DepartmentDoesNotExistException;
import com.nayidisha.empdep.EmployeeDoesNotExistException;
import com.nayidisha.empdep.domain.Department;
import com.nayidisha.empdep.rest.ManagementResource;
import com.nayidisha.empdep.rest.ManagementResourceAssembler;
import com.nayidisha.empdep.service.ManagementService;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

	private final ManagementService managementService;
	private final ManagementResourceAssembler managementResourceAssembler;
	
	@Autowired
	public DepartmentController(ManagementService managementService, ManagementResourceAssembler managementResourceAssembler) {
		this.managementService = managementService;
		this.managementResourceAssembler = managementResourceAssembler;
	}
	
    @RequestMapping(method = RequestMethod.POST, value = "")
    ResponseEntity<ManagementResource> createDepartment(@RequestBody Map<String, String> body) {
        Department dep = managementService.createDepartment(body.get("name"));

        ManagementResource resource = managementResourceAssembler.toResource(null, dep);
        resource.add(linkTo(DepartmentController.class).withSelfRel());
        return new ResponseEntity<ManagementResource>(resource, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{departmentId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<ManagementResource> showDepartment(@PathVariable Long departmentId) throws DepartmentDoesNotExistException {
        Department department = this.managementService.getDepartment(departmentId);
        ManagementResource resource = this.managementResourceAssembler.toResource(null, department);
        resource.add(linkTo(DepartmentController.class).slash(department.getId()).withSelfRel());
        return new ResponseEntity<ManagementResource>(resource, HttpStatus.OK);
    }

    
    @RequestMapping(method = RequestMethod.PUT, value = "")
    ResponseEntity<ManagementResource> assignEmployeeToDepartment(@RequestBody Map<String, String> body) throws DepartmentDoesNotExistException, EmployeeDoesNotExistException{
    	String departmentId = body.get("departmentId");
    	String employeeId = body.get("employeeId");
    	if (departmentId != null && employeeId != null){
	    	managementService.assignManagerToDepartment(Long.parseLong(body.get("departmentId")), Long.parseLong(body.get("employeeId")));
    	}
    	
    	ManagementResource resource = managementResourceAssembler.toResource(null, managementService.getDepartment(Long.parseLong(departmentId)));
    	resource.add(linkTo(DepartmentController.class).withSelfRel());
        return new ResponseEntity<ManagementResource>(resource, HttpStatus.OK);
    }
      
    @ExceptionHandler
    ResponseEntity<String> handleExceptions(Exception e) {
    	return new ResponseEntity<String>(String.format("{\"Message\":\"%s\"}", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
