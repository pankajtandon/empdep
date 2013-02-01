package com.nayidisha.empdep;

public class EmployeeDoesNotExistException extends Exception {

	private static final long serialVersionUID = 2352364266906324773L;

	public EmployeeDoesNotExistException(Long employeeId) {
        super(String.format("Employee %s does not exist", employeeId));
    }
}
