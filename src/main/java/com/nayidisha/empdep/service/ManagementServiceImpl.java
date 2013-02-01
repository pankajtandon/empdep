package com.nayidisha.empdep.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.nayidisha.empdep.DepartmentDoesNotExistException;
import com.nayidisha.empdep.EmployeeDoesNotExistException;
import com.nayidisha.empdep.domain.Department;
import com.nayidisha.empdep.domain.Employee;
import com.nayidisha.empdep.domain.EmployeeStatus;
import com.nayidisha.empdep.repository.DepartmentRepository;
import com.nayidisha.empdep.repository.EmployeeRepository;

@Service
public class ManagementServiceImpl implements ManagementService { 
		 
	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;
	
	@Inject
	public ManagementServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
	}
 
	public void assignEmployeeToDepartment(Long employeeId, Long departmentId) throws DepartmentDoesNotExistException{
		Employee emp = employeeRepository.retrieve(employeeId);
		Department dep = departmentRepository.retrieve(departmentId);
		if (dep == null){
			throw new DepartmentDoesNotExistException(departmentId);
		}
		emp.setDepId(dep.getId());
		employeeRepository.save(emp);
	}

	public void assignManagerToDepartment(Long departmentId, Long managerId) throws EmployeeDoesNotExistException, DepartmentDoesNotExistException { 
		Employee emp = employeeRepository.retrieve(managerId);
		if (emp == null){
			throw new EmployeeDoesNotExistException( managerId);
		}
		Department dep = departmentRepository.retrieve(departmentId);
		if (dep == null){
			throw new DepartmentDoesNotExistException(departmentId);
		}
		dep.setManagerId(managerId);
		departmentRepository.save(dep);

	}

	public Employee getEmployee(Long employeeId) throws EmployeeDoesNotExistException {   
		Employee emp =  employeeRepository.retrieve(employeeId);
		if (emp == null){
			throw new EmployeeDoesNotExistException(employeeId);
		}
		return emp;
		
	}

	public Department getDepartment(Long departmentId) throws DepartmentDoesNotExistException {
		Department department = departmentRepository.retrieve(departmentId);  
		if (department == null){
			throw new DepartmentDoesNotExistException(departmentId);
		}
		return department;
	}

	public Employee createEmployee(String fName, String lName) {
		return employeeRepository.create(fName,  lName);
		
	}

	public Department createDepartment(String name) {
		return departmentRepository.create(name);
		
	}

	public void fireEmployee(Long employeeId) {
		Employee emp = employeeRepository.retrieve(employeeId);
		emp.setStatus(EmployeeStatus.FIRED);
		employeeRepository.save(emp);
		
	}
	
	public void hireEmployee(Long employeeId) {
		Employee emp = employeeRepository.retrieve(employeeId);
		emp.setStatus(EmployeeStatus.WORKING);
		employeeRepository.save(emp);
		
	}

	public void raiseSalary(Long employeeId, double raisedAmount) {
		Employee emp = employeeRepository.retrieve(employeeId);
		emp.setSalary(emp.getSalary() + raisedAmount);
		employeeRepository.save(emp);
		
	}
	
	public List<Employee> findAllEmployees() {
		return employeeRepository.findAll();
	}

	public List<Department> findAllDepartments() {
		return departmentRepository.findAll();
	}

}
