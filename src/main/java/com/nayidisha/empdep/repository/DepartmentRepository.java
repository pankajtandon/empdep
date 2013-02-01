package com.nayidisha.empdep.repository;

import java.util.List;

import com.nayidisha.empdep.domain.Department;

public interface DepartmentRepository {
	
    Department create(String name);

    Department retrieve(Long id);
    
    void save(Department department);

    void remove(Long id);
    
    List<Department> findAll();
}
