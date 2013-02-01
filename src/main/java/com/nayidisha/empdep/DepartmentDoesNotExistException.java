package com.nayidisha.empdep;

public class DepartmentDoesNotExistException extends Exception {
	private static final long serialVersionUID = -667055310213136595L; 

	public DepartmentDoesNotExistException(Long departmentId) {
        super(String.format("Department %s does not exist", departmentId));
    }
}
