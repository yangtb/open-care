package com.sm.open.care.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class DictionaryUtil {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryUtil.class);

    public static final Map<String, String> getValueMap(final Class clazz) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        if (!(clazz != null  && clazz.isEnum())) {
            return  map;
        }
        try {
            Method methodGetCode = clazz.getMethod("getCode", (Class[]) null);
            Method methodGetNamme = clazz.getMethod("getName", (Class[]) null);
            String key = null;
            String value = null;
            if(methodGetCode != null && methodGetNamme != null) {
                for (Object call : clazz.getEnumConstants()) {
                    key = String.valueOf(methodGetCode.invoke(call, (Object[]) null));
                    value = String.valueOf(methodGetNamme.invoke(call, (Object[]) null));
                    if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                        map.put(key,value);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("枚举类转化Map失败！",e);
        }
        return map;
    }
    public static void main(String[] args) {
//		    Map<String, String> dataMap =  DictionaryUtil.getValueMap(EnabledStatus.class);
//		 	System.out.println("======= test DictionaryUtil.getValueMap(EnabledStatus.class) ->" +dataMap);
    }
}
