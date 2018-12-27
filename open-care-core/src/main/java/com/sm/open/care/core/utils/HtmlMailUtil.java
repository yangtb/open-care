package com.sm.open.care.core.utils;

import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName: HtmlMailUtil
 * @Description: 邮件发送帮助类，用于构建邮件对象并且发送邮件
 * @Author yangtongbin
 * @Date 2018/9/16 16:08
 */
@Component
public class HtmlMailUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlMailUtil.class);

    /**
     * 字符编码集
     */
    private String encoding;
    /**
     * smtp发送服务器
     */
    private String host;
    /**
     * 收件人的邮箱
     */
    private String sender;
    /**
     * 发送人的邮箱
     */
    private String userName;
    /**
     * 发件人在邮件服务器上的注册名称
     */
    private String nickname;
    /**
     * 发件人在邮件服务器上的密码
     */
    private String password;

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 邮件发送
     *
     * @param receiverEmail 收件人的邮箱
     * @param message       要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
     * @param subject       要发送的邮件主题
     * @return
     */
    public boolean send(String[] receiverEmail, String message, String subject) {
        // 发送email  
        HtmlEmail email = new HtmlEmail();
        try {
            // 这里是SMTP发送服务器
            email.setHostName(this.host);
            // 字符编码集的设置  
            email.setCharset(this.encoding);
            // 收件人的邮箱  
            email.addTo(receiverEmail);
            // 发送人的邮箱  
            email.setFrom(this.sender, this.nickname);
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码  
            email.setAuthentication(this.userName, this.password);
            // 要发送的邮件主题  
            email.setSubject(subject);
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签  
            email.setMsg(message);
            // 发送  
            email.send();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>> " + subject + "发送成功，接受帐号：" + receiverEmail.toString() + "， 邮件内容： " + message);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("【邮件发送异常】>>> " + subject + "发送失败，接受帐号：" + receiverEmail.toString() + "， 邮件标题： " + subject, e);
            return false;
        }
    }

}
