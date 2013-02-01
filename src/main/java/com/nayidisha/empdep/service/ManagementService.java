package com.nayidisha.empdep.service;

import java.util.List;

import com.nayidisha.empdep.DepartmentDoesNotExistException;
import com.nayidisha.empdep.EmployeeDoesNotExistException;
import com.nayidisha.empdep.domain.Department;
import com.nayidisha.empdep.domain.Employee;

public interface ManagementService {
	
	public Employee getEmployee(Long employeeId) throws EmployeeDoesNotExistException ;
	
	public Department getDepartment(Long departmentId) throws DepartmentDoesNotExistException;
	
	public Employee createEmployee(String fName, String lName);
	
	public Department createDepartment(String name);

	public void assignEmployeeToDepartment(Long employeeId, Long departmentId) throws DepartmentDoesNotExistException;
	
	public void assignManagerToDepartment(Long departmentId, Long managerId) throws EmployeeDoesNotExistException, DepartmentDoesNotExistException;
	
	public void fireEmployee(Long employeeId);
	
	public void hireEmployee(Long employeeId);
	
	public void raiseSalary(Long employeeId, double raisedAmount);
	
	public List<Employee> findAllEmployees();

	public List<Department> findAllDepartments();
}
