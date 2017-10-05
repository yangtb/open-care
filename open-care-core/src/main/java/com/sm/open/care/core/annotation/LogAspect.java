package com.sm.open.care.core.annotation;

import com.alibaba.fastjson.JSON;
import com.sm.open.care.core.enums.Level;
import com.sm.open.care.core.exception.BizRuntimeException;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @ClassName: LogAspect
 * @Description: 日志切面.
 * @Author: yangtongbin
 * @Date: 2017 /6/27 15:56
 */
@Component
@Aspect
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    /**日志模板：入参*/
    private static final String LOG_TEMPLATE_BEFORE     = "【{0}】{1}--begin, requestParams:{2}";
    /**日志模板：出参*/
    private static final String LOG_TEMPLATE_AFTER      = "【{0}】{1}--end, responseResult:{2} , timeCost:{3}ms";
    /**异常日志模板*/
    private static final String LOG_TEMPLATE_EXCEPTION  = "【{0}】{1}--exception, requestParams:{2}";
    /**业务异常日志模板*/
    private static final String LOG_TEMPLATE_BIZ_EXCEPTION  = "【{0}】{1}--exception, errorCode:{2}, message:{3}";

    /**
     *
     * @param point 切入点
     * @param log   日志
     * @return
     * @throws Throwable
     */
    @Around("@annotation(log)")
    public Object around(ProceedingJoinPoint point, Loggable log) throws Throwable {
        // 入参
        String paramsStr = convertParamsToString(point.getArgs());
        // 开始时间
        long start = System.currentTimeMillis();
        // 获取类名
        String className = point.getTarget().getClass().getSimpleName();
        // 获取方法名
        String methodName = point.getSignature().getName();
        // 打印入参日志
        printLogByLevel(log.level(), LOG_TEMPLATE_BEFORE, className + "-" + methodName,
                log.bizDec(), paramsStr);
        Object result = point.proceed();
        // 结束时间
        long end = System.currentTimeMillis();
        // 打印出参日志
        printLogByLevel(log.level(), LOG_TEMPLATE_AFTER, className + "-" + methodName,
                log.bizDec(), "(" + (log.printResult() ? result : "-") + ")", end - start);
        return result;
    }

    /**
     * 异常日志处理
     *
     * @param jPoint    切入点
     * @param log       日志注解
     * @param exception 异常
     */
    @AfterThrowing(value = "@annotation(log)", throwing = "exception")
    public void throwingLog(JoinPoint jPoint, Loggable log, Throwable exception) {
        // 入参
        String paramsStr = convertParamsToString(jPoint.getArgs());
        // 获取类名
        String className = jPoint.getTarget().getClass().getSimpleName();
        // 获取方法名
        String methodName = jPoint.getSignature().getName();
        // 打印日志
        if (exception instanceof BizRuntimeException) {
            LOGGER.info(getMessage(LOG_TEMPLATE_BIZ_EXCEPTION, className + "-" + methodName,
                    log.bizDec(), ((BizRuntimeException) exception).getErrorCode(), exception.getMessage()));
        } else {
            LOGGER.error(getMessage(LOG_TEMPLATE_EXCEPTION, className + "-" + methodName,
                    log.bizDec(), paramsStr), exception);
        }
    }

    /**
     * 入参转换, 敏感参数处理
     *
     * @param params 入参
     * @return
     */
    private String convertParamsToString(Object[] params) {
        StringBuilder sb = new StringBuilder("(");
        if (ArrayUtils.isNotEmpty(params)) {
            for (Object param : params) {
                sb.append(param.getClass().getSimpleName()).append(":")
                        .append(JSON.toJSONString(param)).append(";");
            }
            sb.deleteCharAt(sb.length() - 1);
        } else {
            sb.append("-");
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 根据模板格式化日志.
     *
     * @param template   日志模板
     * @param parameters 模板参数
     * @return the message
     */
    private String getMessage(String template, Object... parameters) {
        return MessageFormat.format(template, parameters);
    }

    /**
     * 打印日志
     *
     * @param level      日志级别
     * @param template   日志模板
     * @param parameters 模板参数
     */
    private void printLogByLevel(Level level, String template, Object... parameters) {
        switch (level) {
            case trace:
                LOGGER.trace(getMessage(template, parameters));
                break;
            case debug:
                LOGGER.debug(getMessage(template, parameters));
                break;
            case info:
                LOGGER.info(getMessage(template, parameters));
                break;
            case warn:
                LOGGER.warn(getMessage(template, parameters));
                break;
            case error:
                LOGGER.error(getMessage(template, parameters));
        }
    }
}
