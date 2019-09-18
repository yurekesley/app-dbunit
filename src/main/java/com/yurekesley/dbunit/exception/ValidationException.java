package com.yurekesley.dbunit.exception;

public class ValidationException extends GenericException {

	private static final long serialVersionUID = 1L;

	public ValidationException(String message) {
		super(TypeError.DANGER, message);
	}

	public ValidationException(String message, Object... params) {
		super(TypeError.DANGER, message, params);
	}
}