package com.nayidisha.empdep.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.nayidisha.empdep.domain.Employee;

@Component
public class MapBasedEmployeeRepository implements EmployeeRepository{
 
	private final AtomicLong idGenerator = new AtomicLong(); 
	private final Map<Long, Employee> employeeMap = new HashMap<Long, Employee>();
	
	public Employee create(String fName, String lName) {
		Employee emp = null;
		synchronized (this) {
			Long id = this.idGenerator.getAndIncrement();
			emp =  new Employee(id, fName, lName);
			employeeMap.put(id,  emp);
		}
		return emp;
	}

	public Employee retrieve(Long id) {
        synchronized (this) {
            if (this.employeeMap.containsKey(id)) {
                return this.employeeMap.get(id);
            }
        }
        return null;
	}

	public void remove(Long id) {
        synchronized (this) {
            if (this.employeeMap.containsKey(id)) {
               employeeMap.remove(id);
            }
        }
		
	}

	public List<Employee> findAll() {
		return new ArrayList<Employee>(employeeMap.values());
	}

	public void save(Employee employee) {
		Assert.notNull(employee.getId());
		employeeMap.put(employee.getId(), employee);
		
	}

}
