package com.sm.open.care.core.utils;

import java.util.Random;

/**
 * @ClassName: RandomUtil
 */
public class RandomUtil {

    /**
     * 获取指定长度的随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 6位随机数
     *
     * @return
     */
    public static int getFourDigitsRandom() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }

}
