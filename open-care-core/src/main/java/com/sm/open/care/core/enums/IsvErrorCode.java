package com.sm.open.care.core.enums;

/**
 * ISV的错误返回值
 *
 */
public enum IsvErrorCode {

    SYSTEM_ERROR("SYSTEM_ERROR", "系统错误"),
    INVALID_PARAMETER("INVALID_PARAMETER", "参数无效");

    private String code;
    private String desc;

    private IsvErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return code;
    }

}
