package com.sm.open.care.core.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description: MD5相关帮助类
 * @ClassName: MD5Util
 */
public class MD5Util {

    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
    	text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    
    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param sign 签名结果
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
    	text = text + key;
    	String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
    	if(mysign.equals(sign)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
    
    /**
	 * @Description: 加签，返回加密字符串
	 * @param paramMap		参数map
	 * @param signKey		加签key值
	 * @param charset		加签字符集
	 * @return
	 */
	public static String sign(Map<String, Object> paramMap, String signKey, String charset) {
		List<String> keys = new ArrayList<String>(paramMap.keySet());
		Collections.sort(keys);
		StringBuffer sb = new StringBuffer(); 
		for (String key : keys) {
			if (StringUtils.isBlank(MapUtil.getString(paramMap, key))) {
				continue;
			}
			sb.append("&").append(key).append("=") .append(MapUtil.getString(paramMap, key));
		}
		return MD5Util.sign(sb.deleteCharAt(0).toString(), signKey, charset);
	}
	
	/**
	 * @Description: 验签，返回验签结果，成功返回true，是否返回false
	 * @param paramMap			参数map
	 * @param removeKeys		需要从参数map中移除的验签参数
	 * @param signMapKey		参数map中签名串的key值
	 * @param signKey			解签密钥的key值
	 * @param charset			解签字符集
	 */
	public static boolean verify(Map<String, Object> paramMap, List<String> removeKeys, String signMapKey, String signKey, String charset) {
		String oldSign = MapUtil.getString(paramMap, signMapKey);
		paramMap.remove(signMapKey);
		if(removeKeys != null && removeKeys.size() != 0) {
			for(String key : removeKeys) {
				paramMap.remove(key);
			}
		}
		List<String> keys = new ArrayList<String>(paramMap.keySet());
		Collections.sort(keys);
		StringBuffer sb = new StringBuffer(); 
		for (String key : keys) {
			if (StringUtils.isBlank(MapUtil.getString(paramMap, key))) {
				continue;
			}
			sb.append("&").append(key).append("=") .append(MapUtil.getString(paramMap, key));
		}
		return MD5Util.verify(sb.deleteCharAt(0).toString(), oldSign, signKey, charset);
	}

}
