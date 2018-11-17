package com.sm.open.care.core.enums;

/**
 * @Description: 通用状态（1：是，0：否）
 */
public enum YesOrNoNum {

    YES("1","是"),
    NO("0","否");

    private String code;
    private String name;

    private YesOrNoNum(String code, String name) {
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
