package com.sm.open.care.core.enums;

/**
 * 通用状态， 所有有状态的都可以使用
 *
 */
public enum Status {
	
	ENABLED("enabled", "有效"),
	DISABLED("disabled", "无效"),
	DELETE("delete", "删除"),
	PASS("pass", "通过"),
	FAIL("fail", "失败"),
	PUBLISH("publish", "已发布"),
	FREEZE("freeze", "冻结"),
	REVOKE("revoke", "撤销"),
	PENDING_PUB("pending_pub", "待提交"),
	PENDING_CHECK("pending_check", "待审核"),
	PENDING_UPDATE("pending_update", "待更新"),
	EXPIRED("expired", "已过期");


	private String code;
	private String name;

	private Status(String code, String name) {
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
