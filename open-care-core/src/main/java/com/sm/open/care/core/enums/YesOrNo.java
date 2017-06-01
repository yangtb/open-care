package com.sm.open.care.core.enums;

/**
 * @Description: 通用状态（Y：是，N：否）
 */
public enum YesOrNo {

    YES("Y","是"),
    NO("N","否");

    private String code;
    private String name;

    private YesOrNo(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
