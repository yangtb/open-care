package com.sm.open.care.core.utils;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.enums.ValidType;
import com.sm.open.care.core.exception.BizRuntimeException;

import java.util.List;
import java.util.Map;

/**
 * @Description: 参数校验帮助类
 * @ClassName: ValidParamUtil
 */
public class ValidParamUtil {
	
	/**
	 * @Description: 根据参数map，及参数功能校验类校验参数
	 * @param params
	 * @param fpv
	 * @return
	 */
	public static void valid(Map<String,Object> params, FunctionParamValid fpv) {
		String key = null;
		if(fpv == null) {
			return;
		} else if(params == null) {
			for(Map.Entry<String, ValidType> entry:fpv.getValidParams().entrySet()){
				key = entry.getKey();
				valid(key, null,FunctionParamValid.getValidType(fpv, key));
			}
		} else {
			for(Map.Entry<String, ValidType> entry:fpv.getValidParams().entrySet()){
				key = entry.getKey();
				valid(key, params.get(key),FunctionParamValid.getValidType(fpv, key));
			}
		}
	}
	
	/**
	 * @Description: 根据不同的参数校验类型校验参数
	 * @param paramName
	 * @param paramValue
	 * @param vt
	 * @return
	 */
	public static void valid(String paramName, Object paramValue, ValidType vt){
		if(vt == null) {
			return;
		}
 		switch(vt){
 			case NOT_NULL:
 				Assert.notNull(paramValue, ErrorCode.ERROR_PARAM_170001, MessageFormatUtil.format(ErrorMessage.MESSAGE_PARAM_170001, paramName));
 				break;
 			case NOT_EMPTY:
 				Assert.notEmpty(ValueUtil.getString(paramValue), ErrorCode.ERROR_PARAM_170002, MessageFormatUtil.format(ErrorMessage.MESSAGE_PARAM_170002, paramName));
 				break;
 			case NOT_INT:
 				Assert.isTrue(ValueUtil.getInt(paramValue,Integer.MAX_VALUE) != Integer.MAX_VALUE, ErrorCode.ERROR_PARAM_170003, MessageFormatUtil.format(ErrorMessage.MESSAGE_PARAM_170003, paramName, paramValue));
 				break;
			case NOT_LONG:
 				Assert.isTrue(ValueUtil.getLong(paramValue,Long.MAX_VALUE) != Long.MAX_VALUE, ErrorCode.ERROR_PARAM_170003, MessageFormatUtil.format(ErrorMessage.MESSAGE_PARAM_170003, paramName, paramValue));
 				break;
			case NOT_FLOAT:
			case NOT_DOUBLE:
 				Assert.isTrue(ValueUtil.getDouble(paramValue,Double.MAX_VALUE) != Double.MAX_VALUE, ErrorCode.ERROR_PARAM_170004, MessageFormatUtil.format(ErrorMessage.MESSAGE_PARAM_170004, paramName, paramValue));
 				break;
 			case NOT_BOOLEAN:
 				Assert.isTrue((paramValue instanceof Boolean || "true".equals(String.valueOf(paramValue)) || "false".equals(String.valueOf(paramValue))),
 						ErrorCode.ERROR_PARAM_170005, MessageFormatUtil.format(ErrorMessage.MESSAGE_PARAM_170005, paramName, paramValue));
 				break;
 			case NOT_STR_DATE:
 				Assert.notNull(ValueUtil.getDate(paramValue), ErrorCode.ERROR_PARAM_170006, MessageFormatUtil.format(ErrorMessage.MESSAGE_PARAM_170006, paramName,paramValue));
 				break;
 			case NOT_STR_DATETIME:
 				Assert.notNull(ValueUtil.getDateTime(paramValue), ErrorCode.ERROR_PARAM_170007, MessageFormatUtil.format(ErrorMessage.MESSAGE_PARAM_170007, paramName,paramValue));
 				break;
 			default:
 				break;
 		}
	}
	
	/**
	 * @Description: 参数sign验签
	 * @param paramMap			参数map
	 * @param removeKeys		需要从参数map中移除的验签参数
	 * @param signMapKey		参数map中签名串的key值
	 * @param signKey			解签密钥的key值
	 * @param charset			解签字符集
	 * @return
	 */
	public static void verify(Map<String, Object> paramMap, List<String> removeKeys, String signMapKey, String signKey, String charset) {
		if(MD5Util.verify(paramMap, removeKeys, signMapKey, signKey, charset)) {
			throw new BizRuntimeException(ErrorCode.ERROR_PARAM_170008, ErrorMessage.MESSAGE_PARAM_170008);
		}

	}

}
