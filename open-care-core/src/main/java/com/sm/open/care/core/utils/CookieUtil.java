package com.sm.open.care.core.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieUtil {

	private static final String UTF8 = "UTF-8";

	private static final String defaultDesKey = "g174f8jh45gg7tg";

	private static final Logger logger = Logger.getLogger(CookieUtil.class);

	public static void setCookie(HttpServletResponse response, String name,
			String value, Boolean isRemmeber) {
		try {
			String cookieValue = encodeCookieValue(value);
			Cookie cookie = new Cookie(name, cookieValue);
			if (isRemmeber) {
				cookie.setMaxAge(3600);
			}
			cookie.setPath("/");
			cookie.setHttpOnly(true);
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error("设置cookie", e);
		}
	}

	public static void setCookie(HttpServletResponse response, String name,
			String value, int maxAge) {
		try {
			String cookieValue = encodeCookieValue(value);
			Cookie cookie = new Cookie(name, cookieValue);
			cookie.setMaxAge(maxAge);
			cookie.setPath("/");
			cookie.setHttpOnly(true);
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error("设置cookie", e);
		}
	}

	/**
	 * cookie 读取
	 * 
	 * @param req
	 * @return
	 */
	public static String getCookieValue(Cookie cookie) {
		String value = cookie.getValue();
		if (StringUtils.isNotBlank(value)) {
			String decode = null;
			try {
				decode = URLDecoder.decode(value, UTF8);
			} catch (UnsupportedEncodingException e1) {
				logger.error("cookie对utf-8解码异常", e1);
			}
			try {
				return DesUtil.utilDecryptData(decode, defaultDesKey);
			} catch (Exception e) {
				logger.error("cookie对des解密异常", e);
				return decode;
			}
		} else {
			return value;
		}
	}

	/**
	 * cookie 读取
	 *
	 * @param req
	 * @return
	 */
	public static String getCookie(HttpServletRequest req, String name) {
		try {
			Cookie[] reqCookies = req.getCookies();
			if (reqCookies != null) {
				for (Cookie cookie : reqCookies) {
					if (name.equals(cookie.getName())) {
						// cookie中取出值统计解密
						return getCookieValue(cookie);
					}
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("解析cookie出现异常:", e);
			return null;
		}
		return null;
	}

	public static String encodeCookieValue(String value) {
		if (StringUtils.isNotBlank(value)) {
			String tempValue = null;
			try {
				tempValue = DesUtil.utilEncryptData(value, defaultDesKey);
			} catch (Exception e) {
				logger.error("cookie对des编码发生异常", e);
				return value;
			}
			try {
				tempValue = URLEncoder.encode(tempValue, UTF8);
			} catch (UnsupportedEncodingException e) {
				logger.error("cookie对utf-8编码发生异常", e);
			}
			return tempValue;
		}
		return value;
	}

}
