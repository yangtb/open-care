package com.sm.open.care.core.enums;

/**
* @ClassName: IdKindType
* @Description: 证件类型枚举
*/
public enum IdKindType {
	
	/** 身份证号 */
	ID_CARD_NO(0, "身份证"),
	/** 护照 */
	PASSPORT_NO(1, "护照"),
	/** 军官证 */
	OFFICERS_NO(2, "军官证"),
	/** 士兵证 */
	SOLDIER_CARD(3, "士兵证"),
	/** 回乡证 */
	HVPS_NO(4, "回乡证"),
	/** 临时身份证 */
	TEMPORARY_ID_CARD(5, "临时身份证"),
	/** 户口簿 */
	FAMILY_REGISTER_NO(6, "户口簿"),
	/** 警官证 */
	POLICE_OFFICER_CERTIFICATE(7, "警官证"),
	/** 台胞证 */
	MTP_NO(8, "台胞证"),
	/** 营业执照 */
	BUSINESS_LICENSE(9, "营业执照"),
	/** 其它证件 */
	OTHER_CARD(10, "其他证件"),
	/** 驾驶证，驾照 */
	DRIVING_LICENSE(11, "驾驶证");
	
	private int code;
	private String name;

	private IdKindType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
