package com.sm.open.care.core.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.text.MessageFormat;

/**
 * @Description:
 * @ClassName: MessageFromatUtil.java
 * @Package:com.sm.open.care.core.utils
 */
public class MessageFormatUtil {
	
	private MessageFormatUtil(){
		
	}
	
	public static String format(String message, Object... params) {
		if(message == null) {
			return "";
		}
		int size = ArrayUtils.getLength(params);
		if (size > 0) {
			return  MessageFormat.format(message, params);
		}
		return message;
	}

}
