package com.sm.open.care.core.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * @ClassName: RequestUtil
 * @Description: 排序及拼接通用方法
 */
public class RequestUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtil.class);

    /**
     * 排序及拼接通用方法
     *
     * @param sortedParams
     * @return
     */
    public static String paramAppendSort(Map<String, String> sortedParams) {
        StringBuffer content = new StringBuffer();
        ArrayList keys = new ArrayList(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); ++i) {
            String key = (String) keys.get(i);
            String value = (String) sortedParams.get(key);
            if (areNotEmpty(new String[] { key, value })) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                ++index;
            }
        }
        return content.toString();
    }

    /**
     * 通用URl拼接参数
     *
     * @param url
     * @param key
     * @param value
     * @return
     */
    public static String urlAppendParam(String url, String key, String value) {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(key)) {
            return null;
        }
        StringBuffer us = new StringBuffer();
        us.append(url).append(StringUtils.indexOf(url, '?') > 0 ? "&" : "?");
        us.append(key).append("=").append(StringUtils.isBlank(value) ? "" : value);
        return us.toString();
    }

    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values != null && values.length != 0) {
            String[] arr$ = values;
            int len$ = values.length;
            for (int i$ = 0; i$ < len$; ++i$) {
                String value = arr$[i$];
                result &= !isEmpty(value);
            }
        } else {
            result = false;
        }
        return result;
    }

    public static boolean isEmpty(String value) {
        int strLen;
        if (value != null && (strLen = value.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(value.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }
}
