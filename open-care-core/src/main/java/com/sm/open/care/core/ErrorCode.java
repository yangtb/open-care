package com.sm.open.care.core;

/**
 * @Description:
 * @ClassName: ErrorCode.java
 */
public class ErrorCode {
    /**
     * 没有错误，表示成功的编码
     */
    public static final String SUCCESS = "0";

    /* 18.1 通用错误码（1XXX） */
    /**
     * 未指定{0}参数
     */
    public static final String ERROR_GENERAL_110001 = "110001";
    /**
     * 参数{0}的值{1}无效
     */
    public static final String ERROR_GENERAL_110002 = "110002";
    /**
     * 请求正文中不存在{0}对象或属性
     */
    public static final String ERROR_GENERAL_110003 = "110003";
    /**
     * 查询字符串中的起始值大于或等于结束值
     */
    public static final String ERROR_GENERAL_110004 = "110004";
    /**
     * 参数{0}重复出现
     */
    public static final String ERROR_GENERAL_110005 = "110005";
    /**
     * 无效的数据（{0}
     */
    public static final String ERROR_GENERAL_110006 = "110006";
    /**
     * 无效区域识别码{0}
     */
    public static final String ERROR_GENERAL_110007 = "110007";
    /**
     * 类型{0}转换成类型{1}异常
     */
    public static final String ERROR_GENERAL_110008 = "110008";

    /* 18.4 网络错误码（5XXX） */
    /**
     * 访问被拒绝
     */
    public static final String ERROR_NET_150001 = "150001";
    /**
     * 网络异常或超时
     */
    public static final String ERROR_NET_150002 = "150002";
    /**
     * 网络繁忙
     */
    public static final String ERROR_NET_150003 = "150003";
    /**
     * 单发消息频率超出最大限制{0}
     */
    public static final String ERROR_NET_150004 = "150004";
    /**
     * 批量消息频率超出最大限制{0}
     */
    public static final String ERROR_NET_150005 = "150005";

    /* 18.5 系统错误码（6XXX） */
    /**
     * 服务器当前处于只读模式
     */
    public static final String ERROR_SYS_160001 = "160001";
    /**
     * 服务器内部错误
     */
    public static final String ERROR_SYS_160002 = "160002";
    /**
     * 数据库发生异常
     */
    public static final String ERROR_SYS_160003 = "160003";
    /**
     * 系统繁忙
     */
    public static final String ERROR_SYS_160004 = "160004";
    /**
     * 访问资源不存在
     */
    public static final String ERROR_SYS_160005 = "160005";
    /**
     * 请求格式错误
     */
    public static final String ERROR_SYS_160006 = "160006";
    /**
     * 访问资源不存在
     */
    public static final String ERROR_SYS_160007 = "160007";
    /**
     * 该资源无权限访问
     */
    public static final String ERROR_SYS_160008 = "160008";

    //系统参数验证错误与编码
    /**
     * 参数对象为 空
     */
    public static final String ERROR_PARAM_170001 = "170001";
    /**
     * 字串值不能为空
     */
    public static final String ERROR_PARAM_170002 = "170002";
    /**
     * 参数值不是有效的整数
     */
    public static final String ERROR_PARAM_170003 = "170003";
    /**
     * 参数值不是有效的 浮点数
     */
    public static final String ERROR_PARAM_170004 = "170004";
    /**
     * 参数值不是有效的布尔类型
     */
    public static final String ERROR_PARAM_170005 = "170005";
    /**
     * 参数【{%}={%}】值字串不是有效的日期与时间(yyyy-MM-dd)格式
     */
    public static final String ERROR_PARAM_170006 = "170006";
    /**
     * 访参数【{%}={%}】值字串不是有效的日期与时间(yyyy-MM-dd HH:mm:ss)格式
     */
    public static final String ERROR_PARAM_170007 = "170007";
    /**
     * 接口访问参数验签不通过
     */
    public static final String ERROR_PARAM_170008 = "170008";


    /**
     * rpc异常
     */
    public static final String DEFAULT_RPC_INVOKE_EXCEPTION = "rpc.invoke.exception";

    /**
     * 权限不足
     */
    public static final String USER_AUTH_EXCEPTION = "user.auth.exception";

    /**
     * 前端统一tips
     */
    public static final String COMMON_TIPS_CODE = "common.tips";
}
