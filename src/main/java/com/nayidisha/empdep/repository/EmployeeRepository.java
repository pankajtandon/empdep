package com.nayidisha.empdep.repository;

import java.util.List;

import com.nayidisha.empdep.domain.Employee;

public interface EmployeeRepository {
    Employee create(String fName, String lName);

    Employee retrieve(Long id);
    
    void save(Employee employee);

    void remove(Long id);
    
    List<Employee> findAll();
}
