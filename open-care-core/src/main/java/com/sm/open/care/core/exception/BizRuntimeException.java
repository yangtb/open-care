package com.sm.open.care.core.exception;

import org.apache.commons.lang.StringUtils;

public class BizRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 3064458242669384355L;

	private final String errorCode;
	private final String msg;
	private Throwable throwable;

	public BizRuntimeException(String message){
		this(StringUtils.EMPTY, message, null);
	}

	public BizRuntimeException(String errorCode, String msg) {
		this.errorCode = errorCode;
		this.msg = msg;
	}

	public BizRuntimeException(String message, Throwable throwable){
		this(StringUtils.EMPTY, message, throwable);
	}

	public BizRuntimeException(String errorCode, String msg, Throwable throwable) {
		this.errorCode = errorCode;
		this.msg = msg;
		this.throwable = throwable;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public String getMessage() {
		Throwable cause = getCause();
		String message = this.msg;
		if (cause != null) {
			StringBuilder sb = new StringBuilder();
			if (message != null) {
				sb.append("【errorCod】:").append(this.errorCode).append("; ");
			}
			if (message != null) {
				sb.append(this.msg).append("; ");
			}
			sb.append("nested exception is ").append(cause);
			return sb.toString();
		} else {
			return message;
		}
	}

	public Throwable getCaluse() {
		return this.throwable;
	}
	
}
