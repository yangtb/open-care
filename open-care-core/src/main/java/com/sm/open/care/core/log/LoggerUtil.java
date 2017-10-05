/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */
package com.sm.open.care.core.log;

import com.alipay.common.tracer.util.TracerContextUtil;
import com.sm.open.care.core.dubbo.TraceIdUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.text.MessageFormat;

/**
 * <p>模版方式日志打印工具</p>
 * @author genghong.peng
 * @version $Id: LoggerUtil.java, v 0.1 2011-7-6 下午05:02:10 genghong.peng Exp $
 */
public final class LoggerUtil {

    private static final String UNKNOWN      = "unknown";

    private static final String TRACE_MARKER = "[TraceId:{0}] ";

    /**
     * 计算带TraceId的日志信息。<BR>
     *
     * @param template
     * @param parameters
     * @return
     */
    public static String getMessage(String template, Object... parameters) {

        return MessageFormat.format(TRACE_MARKER, getTraceId())
                + MessageFormat.format(template, parameters);
    }

    /**
     * <p>生成调试级别日志</p>
     * <p>
     * 根据带参数的日志模板和参数集合，生成debug级别日志
     * 带参数的日志模板中以{数字}表示待替换为变量的日志点，如a={0}，表示用参数集合中index为0的参数替换{0}处
     * </p>
     * @param logger        logger引用
     * @param template      带参数的日志模板
     * @param parameters    参数集合
     */
    public static void debug(Logger logger, String template, Object... parameters) {
        logger.debug(getMessage(template, parameters));
    }

    /**
     * <p>生成通知级别日志</p>
     * <p>
     * 根据带参数的日志模板和参数集合，生成info级别日志
     * 带参数的日志模板中以{数字}表示待替换为变量的日志点，如a={0}，表示用参数集合中index为0的参数替换{0}处
     * </p>
     * @param logger        logger引用
     * @param template      带参数的日志模板
     * @param parameters    参数集合
     */
    public static void info(Logger logger, String template, Object... parameters) {
        logger.info(getMessage(template, parameters));
    }

    /**
     * <p>生成警告级别日志</p>
     * <p>
     * 根据带参数的日志模板和参数集合，生成warn级别日志
     * 带参数的日志模板中以{数字}表示待替换为变量的日志点，如a={0}，表示用参数集合中index为0的参数替换{0}处
     * </p>
     * @param logger        logger引用
     * @param template      带参数的日志模板
     * @param parameters    参数集合
     */
    public static void warn(Logger logger, String template, Object... parameters) {
        logger.warn(getMessage(template, parameters));
    }

    /**
     * <p>生成警告级别日志</p>
     * <p>带异常堆栈</p>
     * @param e
     * @param logger
     * @param template
     * @param parameters
     */
    public static void warn(Throwable e, Logger logger, String template, Object... parameters) {
        logger.warn(getMessage(template, parameters), e);
    }

    /**
     * <p>生成错误级别日志</p>
     * <p>
     * 根据带参数的日志模板和参数集合，生成error级别日志
     * 带参数的日志模板中以{数字}表示待替换为变量的日志点，如a={0}，表示用参数集合中index为0的参数替换{0}处
     * </p>
     * @param e             错误异常堆栈
     * @param logger        logger引用
     * @param template      带参数的日志模板
     * @param parameters    参数集合
     */
    public static void error(Throwable e, Logger logger, String template, Object... parameters) {
        logger.error(getMessage(template, parameters), e);
    }

    /**
     * <p>生成错误级别日志</p>
     * <p>
     * 根据带参数的日志模板和参数集合，生成error级别日志
     * 带参数的日志模板中以{数字}表示待替换为变量的日志点，如a={0}，表示用参数集合中index为0的参数替换{0}处
     * </p>
     * @param logger        logger引用
     * @param template      带参数的日志模板
     * @param parameters    参数集合
     */
    public static void error(Logger logger, String template, Object... parameters) {
        logger.error(getMessage(template, parameters));
    }

    /**
     * 获取当前上下文中的traceid
     * @return
     */
    private static String getTraceId() {
        String traceId = TraceIdUtil.getLocalTraceid();
        if (StringUtils.isNotBlank(traceId)) {
            return traceId;
        }
        return UNKNOWN;
    }
}
