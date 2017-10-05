package com.sm.open.care.core.enums;

/**
 * @ClassName: Level
 * @Description: 日志级别枚举
 * @Author: yangtongbin
 * @Date: 2017 /6/29 09:42
 */
public enum Level {
    trace,
    debug,
    info,
    warn,
    error;

    private String value;

    Level() {
    }

    Level(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
