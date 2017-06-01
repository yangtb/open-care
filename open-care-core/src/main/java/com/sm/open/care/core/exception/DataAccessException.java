package com.sm.open.care.core.exception;

public abstract class DataAccessException extends NestedRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3526617119116538755L;

	public DataAccessException(String msg) {
		super(msg);
	}

	public DataAccessException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
