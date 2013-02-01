package com.nayidisha.empdep.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.nayidisha.empdep.domain.Employee;
import com.nayidisha.empdep.domain.EmployeeStatus;
import com.nayidisha.empdep.rest.ManagementResource;
import com.nayidisha.empdep.rest.ManagementResourceAssembler;
import com.nayidisha.empdep.service.ManagementService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private final ManagementService managementService;
	private final ManagementResourceAssembler managementResourceAssembler;

	@Autowired
	public EmployeeController(ManagementService managementService, ManagementResourceAssembler managementResourceAssembler) {
		this.managementService = managementService;
		this.managementResourceAssembler = managementResourceAssembler;
	}
	
    @RequestMapping(method = RequestMethod.POST, value = "")
    ResponseEntity<ManagementResource> createEmployee(@RequestBody Map<String, String> body) {
        Employee emp = managementService.createEmployee(body.get("fName"), body.get("lName")) ; 

        ManagementResource resource = managementResourceAssembler.toResource(emp, null);
        resource.add(linkTo(EmployeeController.class).withSelfRel());
        return new ResponseEntity<ManagementResource>(resource, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/{employeeId}")
    ResponseEntity<ManagementResource> manageEmployee(@PathVariable Long employeeId, @RequestBody Map<String, String> body) throws DepartmentDoesNotExistException, EmployeeDoesNotExistException {
    	String departmentId = body.get("departmentId");
    	Employee employee = null;
    	Department department = null;
    	
    	if (employeeId != null){
    		employee = managementService.getEmployee(employeeId);
    	}
    	if (!StringUtils.isEmpty(departmentId)){
    		department = managementService.getDepartment(Long.parseLong(departmentId));
    	}    	
    	
    	if (employeeId != null && !StringUtils.isEmpty(departmentId)){
    		managementService.assignEmployeeToDepartment( employeeId, Long.parseLong(body.get("departmentId")));
    	}
    	String status = body.get("status");
    	if ((employeeId != null) && !StringUtils.isEmpty(status) && (status.equals(EmployeeStatus.FIRED.toString()))){
    		managementService.fireEmployee(employeeId);
    	}
    	if ((employeeId != null) && !StringUtils.isEmpty(status) && (status.equals(EmployeeStatus.WORKING.toString()))){
    		managementService.hireEmployee(employeeId); 
    	}    	
    	String raise = body.get("raise");
    	if ((employeeId != null) && !StringUtils.isEmpty(raise) ) {
    		managementService.raiseSalary(employeeId, Long.parseLong(raise));
    	}

        ManagementResource resource = managementResourceAssembler.toResource(employee, department);
        resource.add(linkTo(EmployeeController.class).withSelfRel());
        return new ResponseEntity<ManagementResource>(resource, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{employeeId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<ManagementResource> showEmployee(@PathVariable Long employeeId) throws EmployeeDoesNotExistException {
        Employee employee = managementService.getEmployee(employeeId);
        Department department = null;
        if (employee.getDepId() != null){
        	try {
        		department = managementService.getDepartment(employee.getDepId());
        	} catch (DepartmentDoesNotExistException ddne){
        		//Ignore
        	}
        }
        ManagementResource resource = managementResourceAssembler.toResource(employee, department);
        resource.add(linkTo(EmployeeController.class).slash(employee.getId()).withSelfRel());
        return new ResponseEntity<ManagementResource>(resource, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<List<ManagementResource>> showEmployees()  {
        List<Employee> employeeList = managementService.findAllEmployees();

        List<ManagementResource> resourceList = managementResourceAssembler.toResource(employeeList, null);
        //resource.add(linkTo(EmployeeController.class).slash(employee.getId()).withSelfRel());
        return new ResponseEntity<List<ManagementResource>> (resourceList, HttpStatus.OK);
    }
    
    @ExceptionHandler
    ResponseEntity<String> handleExceptions(Exception e) {
    	return new ResponseEntity<String>(String.format("{\"Message\":\"%s\"}", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
