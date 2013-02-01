package com.nayidisha.empdep.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.nayidisha.empdep.domain.Department;

@Component
public class MapBasedDepartmentRepository implements DepartmentRepository{
 
	private final AtomicLong idGenerator = new AtomicLong(); 
	private final Map<Long, Department> depMap = new HashMap<Long, Department>();
	
	public Department create(String name) {
		Department dep = null;
		synchronized (this) {
			Long id = this.idGenerator.getAndIncrement();
			dep =  new Department(id, name); 
			depMap.put(id,  dep);
		}
		return dep;
	}

	public Department retrieve(Long id) {
        synchronized (this) {
            if (this.depMap.containsKey(id)) {
                return this.depMap.get(id);
            }
        }
        return null;
	}

	public void remove(Long id) {
        synchronized (this) {
            if (this.depMap.containsKey(id)) {
               depMap.remove(id);
            }
        }
		
	}

	public List<Department> findAll() {
		return new ArrayList<Department>(depMap.values());
	}

	public void save(Department department) {
		Assert.notNull(department.getId());
		depMap.put(department.getId(), department);
	}

}
