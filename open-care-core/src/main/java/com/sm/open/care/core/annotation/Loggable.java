package com.sm.open.care.core.annotation;


import com.sm.open.care.core.enums.Level;

import java.lang.annotation.*;

/**
 * @ClassName: Loggable
 * @Description: 日志打印注解
 * @Author: yangtongbin
 * @Date: 2017 /6/27 14:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Loggable {

    /**
     * 业务描述
     */
    String bizDec() default "";

    /**
     * 日志级别
     */
    Level level() default Level.debug;

    /**
     * 是否打印返回结果
     */
    boolean printResult() default false;

}
