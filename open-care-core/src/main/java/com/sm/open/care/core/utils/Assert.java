package com.sm.open.care.core.utils;

import com.sm.open.care.core.exception.BizRuntimeException;

/**
 * @Description: 断言类，校验基本数据及表达式
 * @ClassName: Assert
 * @Package: com.sm.open.care.core.utils
 */
public class Assert {

	public static void isTrue(boolean expression, String errorNo, String message) {
		if (!expression) {
			throw new BizRuntimeException(errorNo, message);
		}
	}

	public static void isNull(Object object, String errorNo, String message) {
		if (object != null) {
			throw new BizRuntimeException(errorNo, message);
		}
	}

	public static void notNull(Object object, String errorNo, String message) {
		if (object == null) {
			throw new BizRuntimeException(errorNo, message);
		}
	}

	public static void notEmpty(Object object, String errorNo, String message) {
		if (ValueUtil.isEmpty(object)) {
			throw new BizRuntimeException(errorNo, message);
		}
	}

}
