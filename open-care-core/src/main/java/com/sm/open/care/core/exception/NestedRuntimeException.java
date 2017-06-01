package com.sm.open.care.core.exception;

public abstract class NestedRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 5439915454935047936L;

	static {
		NestedRuntimeException.class.getName();
	}

	public NestedRuntimeException(String msg) {
		super(msg);
	}

	public NestedRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public String getMessage() {
		Throwable cause = getCause();
		String message = super.getMessage();
		if (cause != null) {
			StringBuilder sb = new StringBuilder();
			if (message != null) {
				sb.append(message).append("; ");
			}
			sb.append("nested exception is ").append(cause);
			return sb.toString();
		} else {
			return message;
		}
	}

	public Throwable getRootCause() {
		Throwable rootCause = null;
		Throwable cause = getCause();
		while ((cause != null) && (cause != rootCause)) {
			rootCause = cause;
			cause = cause.getCause();
		}
		return rootCause;
	}

	public Throwable getMostSpecificCause() {
		Throwable rootCause = getRootCause();
		return ((rootCause != null) ? rootCause : this);
	}

	@SuppressWarnings("rawtypes")
	public boolean contains(Class exType) {
		if (exType == null) {
			return false;
		}
		if (exType.isInstance(this)) {
			return true;
		}
		Throwable cause = getCause();
		if (cause == this) {
			return false;
		}
		if (cause instanceof NestedRuntimeException) {
			return ((NestedRuntimeException) cause).contains(exType);
		}
		do {
			if (exType.isInstance(cause)) {
				return true;
			}
			if (cause.getCause() == cause) {
				break;
			}
			cause = cause.getCause();
		} while (cause != null);

		return false;
	}
}
