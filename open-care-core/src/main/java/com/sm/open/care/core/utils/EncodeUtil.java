package com.sm.open.care.core.utils;

import com.sm.open.care.core.enums.CharsetType;
import com.sm.open.care.core.log.LoggerUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * @ClassName: EncodeUtil
 * @Description: Encode、Base64编码、Base64解码工具类
 */
public class EncodeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncodeUtil.class);

    /**
     * encode编码
     *
     * @param value
     * @return
     */
    public static String encode(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return java.net.URLEncoder.encode(value, CharsetType.UTF_8.getName());
        } catch (Exception e) {
            LoggerUtil.error(LOGGER, "encode Error : {0}", e.getMessage());
        }
        return value;
    }

    /**
     * decode解码
     *
     * @param value
     * @return
     */
    public static String decode(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return java.net.URLDecoder.decode(value, CharsetType.UTF_8.getName());
        } catch (Exception e) {
            LoggerUtil.error(LOGGER, "decode Error : {0}", e.getMessage());
        }
        return value;
    }

    /**
     * 判断url是否被编码
     *
     * @param url
     * @return
     */
    public static boolean isEncode(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        try {
            if (!StringUtils.equalsIgnoreCase(decode(url), url)) {
                return true;
            }
        } catch (Exception e) {
            LoggerUtil.error(LOGGER, "isEncode Error : {0}", e.getMessage());
        }
        return false;
    }

    /**
     * 判断url是否包含左右花括号
     *
     * @param url
     * @return
     */
    public static String replaceBrace(String url) {
        if (StringUtils.isBlank(url)) {
            return url;
        }
        try {
            if (StringUtils.contains(url, "%7b") && StringUtils.contains(url, "%7d")) {
                return StringUtils.replace(StringUtils.replace(url, "%7b", "{"), "%7d", "}");
            }
        } catch (Exception e) {
            LoggerUtil.error(LOGGER, "replaceBrace Error : {0}", e.getMessage());
        }
        return url;
    }

    /**
     * Base64编码
     *
     * @param value
     * @return
     */
    public static String encodeBase64(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            byte[] encodeBase64 = Base64.encodeBase64(value.getBytes(CharsetType.UTF_8.getName()));
            value = new String(encodeBase64, CharsetType.UTF_8.getName());
        } catch (UnsupportedEncodingException e) {
            LoggerUtil.error(LOGGER, "encodeBase64 Error : {0}", e.getMessage());
        }
        return value;
    }

    /**
     * Base64解码
     *
     * @param value
     * @return
     */
    public static String decodeBase64(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            byte[] encodeBase64 = Base64.decodeBase64(value.getBytes(CharsetType.UTF_8.getName()));
            value = new String(encodeBase64, CharsetType.UTF_8.getName());
            value = java.net.URLDecoder.decode(value, CharsetType.UTF_8.getName());
        } catch (Exception e) {
            LoggerUtil.error(LOGGER, "EncoderByMd5 Error : {0}", e.getMessage());
        }
        return value;
    }

    /**
     * 利用MD5进行加密
     *
     * @param str 待加密的字符串
     * @return 加密后的字符串
     */
    public static String EncoderByMd5(String str) {
        MessageDigest md5 = null;
        String newstr = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));//加密后的字符串
        } catch (Exception e) {
            LoggerUtil.error(LOGGER, "EncoderByMd5 Error : {0}", e.getMessage());
        }
        return newstr;
    }
}
