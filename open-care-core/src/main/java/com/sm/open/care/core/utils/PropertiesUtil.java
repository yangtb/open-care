package com.sm.open.care.core.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName: PropertiesUtil
 * @Description: 属性文件操作类
 * @Author yangtongbin
 * @Date 2018/9/18 15:07
 */
public class PropertiesUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * 读取properties属性文件
     *
     * @param relativePath ClassPathResource下相对路径
     * @return
     */
    public static Properties getProperty(String relativePath) {
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(
                    new EncodedResource(new ClassPathResource(relativePath), "UTF-8"));
            return properties;
        } catch (Exception e) {
            LOGGER.error("读取properties文件错误，relativePath: {}" + relativePath, e);
            return null;
        }
    }

    /**
     * 读取properties属性文件
     *
     * @param relativePath getClassLoader相对路径
     * @return
     */
    public static Properties readProperty(String relativePath) {
        InputStream is = null;
        try {
            is = PropertiesUtil.class.getClassLoader().getResourceAsStream(relativePath);
            Properties p = new Properties();
            p.load(is);
            return p;
        } catch (Exception e) {
            LOGGER.error("读取properties文件错误，relativePath: {}" + relativePath, e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    /**
     * 读取properties属性文件
     *
     * @param data         写入数据map
     * @param relativePath getClassLoader相对路径
     * @param comments     注释
     */
    public static void setProperty(Map<String, String> data, String relativePath, String comments) {
        Properties properties = new Properties();
        OutputStream out = null;
        try {
            if (data.isEmpty()) {
                return;
            }
            data.forEach((k, v) -> {
                if (v == null) {
                    v = "";
                }
                properties.setProperty(k, v);
            });
            out = new FileOutputStream(relativePath);
            properties.store(out, comments);
        } catch (Exception e) {
            LOGGER.error("写入properties文件错误，relativePath: {}" + relativePath, e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
            }
        }
    }

}
