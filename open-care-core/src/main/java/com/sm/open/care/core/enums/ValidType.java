package com.sm.open.care.core.enums;

/**
 * @Description: 
 * @ClassName: ValidType.java
 */
public enum ValidType {
	
	NOT_NULL(0,"参数对象值不能为空"),
	NOT_EMPTY(1,"参数字串值不能为空"),
	
	NOT_INT(11,"参数值不是有效的整数"),
	NOT_LONG(12,"参数值不是有效的长整数"),
	NOT_FLOAT(13,"参数值不是有效的浮点数"),
	NOT_DOUBLE(14,"参数值不是有效的浮点数"),
	NOT_BOOLEAN(15,"参数值不是有效的布尔类型"),  
	
	NOT_STR_DATE(21,"参数值字串不是有效的日期(格式)"),
	NOT_STR_DATETIME(22,"参数值字串不是有效的日期与时间(格式)");
	
	private ValidType(int type,String name){
		this.type = type;
		this.name =name;
	}

	private int type;			// 参数验证类型值 value
	private String name;		// 参数验证类型名称
	public int getType() {
		return type;
	}
	public String getName() {
		return name;
	}	
}
