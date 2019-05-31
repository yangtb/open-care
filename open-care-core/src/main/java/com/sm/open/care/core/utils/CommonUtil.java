package com.sm.open.care.core.utils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.UUID;


public class CommonUtil {

    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * freemarker 短信/邮件模板配置
     * @param input
     * @param templateStr
     * @return
     */
    @SuppressWarnings({"rawtypes", "deprecation"})
    public static String freemarkerParser(Map input, String templateStr) {
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        String template = "content";
        stringLoader.putTemplate(template, templateStr);
        Configuration cfg = new Configuration();
        cfg.setTemplateLoader(stringLoader);
        try {
            Template templateCon = cfg.getTemplate(template);
            StringWriter writer = new StringWriter();
            templateCon.process(input, writer);
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取uuid
     *
     * @return
     */
    public static String uuid() {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }

}

 
