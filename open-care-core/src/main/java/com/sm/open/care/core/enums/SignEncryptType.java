package com.sm.open.care.core.enums;

/**
 * @ClassName: SignEncryptType
 * @Description: 加密类型
 */
public enum SignEncryptType {
    MD5(1, "MD5", "MD5"),
    RSA(4, "RSA", "RSA"),
    DSA(5, "DSA", "DSA");

    private int value;
    private String name;
    private String desc;

    private SignEncryptType(int value, String name, String desc) {
        this.value = value;
        this.name = name;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
