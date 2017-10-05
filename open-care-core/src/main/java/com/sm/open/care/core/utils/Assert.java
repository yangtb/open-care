package com.sm.open.care.core.utils;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.exception.BizRuntimeException;

import java.text.MessageFormat;

/**
 * @ClassName: Assert
 * @Description: 断言类，校验基本数据及表达式
 * @Author yangtongbin
 * @Date 2017/9/11 14:25
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

    /**
     * 未指定参数断言
     *
     * @param object    判断对象
     * @param arguments 参数
     */
    public static void isNull(Object object, String arguments) {
        isNull(object,
                ErrorCode.ERROR_GENERAL_110001,
                MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, arguments));
    }

    /**
     * 未指定参数断言
     *
     * @param expression true | false
     * @param arguments  参数
     */
    public static void isTrue(boolean expression, String arguments) {
        isTrue(expression,
                ErrorCode.ERROR_GENERAL_110001,
                MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, arguments));
    }

}
