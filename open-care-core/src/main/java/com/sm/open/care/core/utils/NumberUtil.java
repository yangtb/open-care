package com.sm.open.care.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 数字转换工具
 *
 */
public class NumberUtil {

	private static final BigDecimal TEN_THOUSAND = new BigDecimal(10000L); // 1万

	private static final BigDecimal ONE_HUNDRED_MILLION = new BigDecimal(100000000L); // 1亿

	public static String formatValue(long number) {
		BigDecimal decimal = new BigDecimal(number);
		if (decimal.compareTo(TEN_THOUSAND) < 0) {
			return String.valueOf(number);
		} else if (decimal.compareTo(ONE_HUNDRED_MILLION) < 0) {
			BigDecimal val = decimal.divide(TEN_THOUSAND);
			return formatByCeiling(val, 1) + "万";
		} else {
			BigDecimal val = decimal.divide(ONE_HUNDRED_MILLION);
			return formatByCeiling(val, 1) + "亿";
		}
	}

	/**
	 * 四舍五入法格式化数值，保留若干位小数
	 * 
	 * @param target
	 *            待格式化目标数值
	 * @param digits
	 *            保留位数
	 */
	public static String formatByHalfUp(BigDecimal target, int digits) {
		return format(target, digits, RoundingMode.HALF_UP);
	}

	/**
	 * 进一法格式化数值，保留若干位小数
	 * 
	 * @param target
	 *            待格式化目标数值
	 * @param digits
	 *            保留位数
	 */
	public static String formatByCeiling(BigDecimal target, int digits) {
		return format(target, digits, RoundingMode.CEILING);
	}

	/**
	 * 指定模式格式化数值，保留若干位小数
	 * 
	 * @param target
	 *            待格式化目标数值
	 * @param digits
	 *            保留位数
	 */
	public static String format(BigDecimal target, int digits, RoundingMode mode) {
		StringBuilder pattern = new StringBuilder("0.");
		for (int i = 0; i < digits; i++) {
			pattern.append("0");
		}
		DecimalFormat df = new DecimalFormat(pattern.toString());
		df.setRoundingMode(mode);
		return df.format(target);
	}

	/**
	 * 使用java正则表达式去掉多余的.与0
	 *
	 * @param s 数字字符串值
	 * @return
	 */
	public static String stripTrailingZeros(String s){
		if(s.indexOf(".") > 0){
			s = s.replaceAll("0+?$", "");	//	去掉多余的0
			s = s.replaceAll("[.]$", "");	//	如最后一位是.则去掉
		}
		return s;
	}

	public static void main(String[] args) {
		System.out.println(formatValue(19001L)); // 2.0万
		System.out.println(formatValue(10001L)); // 1.1万
		System.out.println(stripTrailingZeros("183.000"));
	}

}
