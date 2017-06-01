package com.sm.open.care.core.utils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public class TimeTextUtil {

    public static String converTime(Date gmtTime) {
        if (null == gmtTime) {
            return "";
        }
        Date today = new Date();
        Date yesterday = DateUtils.addDays(today, -1 );
        if (DateUtils.isSameDay(gmtTime, today)) {
            // 当天
            Long mins = (today.getTime() - gmtTime.getTime()) / (1000 * 60);
            if (mins < 60) {
                return 0 < mins  ? (mins + "分钟前") : "刚刚";
            } else {
                return DateFormatUtils.format(gmtTime, "HH:mm");
            }
        } else if (DateUtils.isSameDay(gmtTime, yesterday)) {
            return DateFormatUtils.format(gmtTime, "昨天 HH:mm");
        } else if (DateUtils.truncate(today, DateUtils.SEMI_MONTH).equals(DateUtils.truncate(gmtTime, DateUtils.SEMI_MONTH))) {
            return DateFormatUtils.format(gmtTime, "MM-dd HH:mm");
        } else {
            return DateFormatUtils.format(gmtTime, "yyyy-MM-dd HH:mm");
        }
    }
}
