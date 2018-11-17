package com.sm.open.care.core;

/**
 * @Description:
 * @ClassName: ErrorCode.java
 */
public class ErrorMessage {
	
	/* 18.1 通用错误码（1XXX） */
	/**未指定{0}参数*/
	public static final String MESSAGE_GENERAL_110001 = "未指定{0}参数";
	/**参数{0}的值{1}无效*/
	public static final String MESSAGE_GENERAL_110002 = "参数{0}的值{1}无效";
	/**请求正文中不存在{0}对象或属性*/
	public static final String MESSAGE_GENERAL_110003 = "请求正文中不存在{0}对象或属性";
	/**查询字符串中的起始值大于或等于结束值*/
	public static final String MESSAGE_GENERAL_110004 = "查询字符串中的起始值大于或等于结束值";
	/**参数{0}重复出现*/
	public static final String MESSAGE_GENERAL_110005 = "参数{0}重复出现";
	/**无效的数据（{0}*/
	public static final String MESSAGE_GENERAL_110006 = "无效的数据（{0}）";
	/**无效区域识别码{0}*/
	public static final String MESSAGE_GENERAL_110007 = "无效区域识别码{0}";
	/**类型{0}转换成类型{1}异常*/
	public static final String MESSAGE_GENERAL_110008 = "类型{0}转换成类型{1}异常";

	/* 18.4 网络错误码（5XXX） */
	/**访问被拒绝*/
	public static final String MESSAGE_NET_150001 = "访问被拒绝";
	/** 网络异常或超时*/
	public static final String MESSAGE_NET_150002 = "网络异常或超时";
	/**网络繁忙*/
	public static final String MESSAGE_NET_150003 = "网络繁忙";
	/**单发消息频率超出最大限制{0} */
	public static final String MESSAGE_NET_150004 = "单发消息频率超出最大限制{0}";
	/**批量消息频率超出最大限制{0} */
	public static final String MESSAGE_NET_150005 = "批量消息频率超出最大限制{0}";

	/* 18.5 系统错误码（6XXX） */
	/**服务器当前处于只读模式*/
	public static final String MESSAGE_SYS_160001 = "服务器当前处于只读模式";
	/**服务器内部错误*/
	public static final String MESSAGE_SYS_160002 = "服务器内部错误";
	/**数据库发生异常*/
	public static final String MESSAGE_SYS_160003 = "数据库发生异常";
	/**系统繁忙*/
	public static final String MESSAGE_SYS_160004 = "系统繁忙";
	/**访问资源不存在*/
	public static final String MESSAGE_SYS_160005 = "访问资源不存在";
	/**请求格式错误 */
	public static final String MESSAGE_SYS_160006 = "请求格式错误";
	/**访问资源不存在*/
	public static final String MESSAGE_SYS_160007 = "访问资源不存在";
	/**该资源无权限访问*/
	public static final String MESSAGE_SYS_160008 = "该资源无权限访问";

	// 参数验证错误与提示 
	/**参数【{0}】对象为 空 */
	public static final String MESSAGE_PARAM_170001 = "参数【{0}】对象为 空 ";
	/**参数【{0}】字串值不能为空*/
	public static final String MESSAGE_PARAM_170002 = "参数【{0}】字串值不能为空";
	/**参数【{0}={1}】值不是有效的整数*/
	public static final String MESSAGE_PARAM_170003 = "参数【{0}={1}】值不是有效的整数";
	/**参数【{0}={1}】值不是有效的浮点数*/
	public static final String MESSAGE_PARAM_170004 = "参数【{0}={1}】值不是有效的浮点数";
	/**参数【{0}={1}】值不是有效的布尔类型*/
	public static final String MESSAGE_PARAM_170005 = "参数【{0}={1}】值不是有效的布尔类型";
	/**参数【{0}={1}】值字串不是有效的日期与时间(yyyy-MM-dd)格式 */
	public static final String MESSAGE_PARAM_170006 = "参数【{0}={1}】值字串不是有效的日期与时间(yyyy-MM-dd)格式";
	/**访参数【{0}={1}】值字串不是有效的日期与时间(yyyy-MM-dd HH:mm:ss)格式*/
	public static final String MESSAGE_PARAM_170007 = "访参数【{0}={1}】值字串不是有效的日期与时间(yyyy-MM-dd HH:mm:ss)格式";
	/**接口访问参数验签不通过 */
	public static final String MESSAGE_PARAM_170008 = "接口访问参数验签不通过";

	/** rpc异常 */
	public static final String DEFAULT_RPC_INVOKE_EXCEPTION_MSG = "rpc服务异常";

	public static final String USER_AUTH_EXCEPTION_MSG = "只能操作当前用户所在机构数据";

}
