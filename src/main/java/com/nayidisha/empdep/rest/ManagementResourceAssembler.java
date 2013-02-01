package com.nayidisha.empdep.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Component;

import com.nayidisha.empdep.domain.Department;
import com.nayidisha.empdep.domain.Employee;
import com.nayidisha.empdep.domain.EmployeeStatus;
import com.nayidisha.empdep.util.MixUtils;
import com.nayidisha.empdep.web.DepartmentController;
import com.nayidisha.empdep.web.EmployeeController;

/**
 * This class determines the state of the resources (departments and employees) and renders appropriate links.
 * @author pankajtandon
 *
 */
@Component
public class ManagementResourceAssembler {

	public ManagementResource toResource(Employee employee, Department department) {
		
		ManagementResource resource = new ManagementResource();
		
		resource = this.getEmployeeResource(employee, resource);
		
		resource = this.getDepartmentResource(department, resource);
		
		resource.departmentList = new ArrayList<Department>();
		resource.departmentList.add(department);
		
		resource.employeeList  = new ArrayList<Employee>();
		resource.employeeList.add(employee);
		
		return resource;
	}
	
	public List<ManagementResource> toResource(List<Employee> employeeList, List<Department> departmentList) {
		
		List<ManagementResource> mrList = new ArrayList<ManagementResource>();
		ManagementResource resource = new ManagementResource();
		MixUtils<Employee> empUtils = new MixUtils<Employee>();
		for (Employee employee : empUtils.safeList(employeeList) ){
			resource = this.getEmployeeResource(employee, resource);
			mrList.add(resource);
		}
		
		MixUtils<Department> depUtils = new MixUtils<Department>();
		for (Department department : depUtils.safeList(departmentList)) {			
			resource = this.getDepartmentResource(department, resource);
			mrList.add(resource);
		}

		return mrList;
	}
	
	private ManagementResource getEmployeeResource(Employee employee, ManagementResource resource) {
		if (employee != null) {
			if (EmployeeStatus.WORKING.equals(employee.getStatus())) {
				//working employees can be fired, assigned to departments, made managers of departments, and given raises
				resource.add(linkTo(EmployeeController.class).slash(employee.getId()).withRel("fire"));
				
			} else if (EmployeeStatus.FIRED.equals(employee.getStatus())) {
				//Fired employees can be transitioned to WORKING status
				resource.add(linkTo(EmployeeController.class).slash(employee.getId()).withRel("hire"));
				
			} else if (employee == null || employee.getStatus() == null){
				//Employee not yet created. So client can create an employee
				resource.add(linkTo(EmployeeController.class).withRel("createEmployee"));
			}
		}
		return resource;
	}
	
	private ManagementResource getDepartmentResource(Department department, ManagementResource resource){
		if (department != null) {
			//department exists. Manager can be assigned
			resource.add(linkTo(DepartmentController.class).slash(department.getId()).withRel("assignManager"));
		} else {
			//Department not yet created, so clients can create departments.
			resource.add(linkTo(DepartmentController.class).withRel("createDepartment"));
		}	
		return resource;
	}
}
