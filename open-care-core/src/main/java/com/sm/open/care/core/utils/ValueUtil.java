package com.sm.open.care.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @ClassName: ValueUtil.java
 */
public class ValueUtil {
    /**
     * 标准日期+时间格式, 长度： 10+8+1=19
     **/
    public final static SimpleDateFormat DF_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 标准时间格式, 长度： 8
     **/
    public final static SimpleDateFormat DF_TIME = new SimpleDateFormat("HH:mm:ss");
    /**
     * 标准日期格式 , 长度：10
     **/
    public final static SimpleDateFormat DF_DATE = new SimpleDateFormat("yyyy-MM-dd");

    private static final Logger log = LoggerFactory.getLogger(ValueUtil.class);

    private static Object lock = new Object();

    private ValueUtil() {

    }

    public static String getString(Object object, String defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        return String.valueOf(object);
    }

    public static String getString(Object object) {
        return getString(object, "");
    }

    public static int getInt(Object object, int defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int getInt(Object object) {
        return getInt(object, 0);
    }

    public static long getLong(Object object, long defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).longValue();
        }
        try {
            return Long.parseLong(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long getLong(Object object) {
        return getLong(object, 0);
    }

    public static double getDouble(Object object) {
        return getDouble(object, 0);
    }

    public static double getDouble(Object object, double defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).doubleValue();
        }
        try {
            return Double.parseDouble(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(Object object, boolean defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        return Boolean.valueOf(String.valueOf(object));
    }

    public static boolean getBoolean(Object object) {
        return getBoolean(object, false);
    }

    public static Boolean getBooleanObj(Object object, Boolean defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        return getBoolean(object);
    }

    public static Boolean getBooleanObj(Object object) {
        return getBooleanObj(object, null);
    }

    /**
     * 格式解析(yyyy-MM-dd)
     **/
    public static Date getDate(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Date) {
            return (Date) object;
        } else {
            return (getDate(getString(object, "")));
        }
    }

    /**
     * 格式解析(yyyy-MM-dd HH:mm:ss)
     **/
    public static Date getDateTime(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Date) {
            return (Date) object;
        } else {
            return (getDateTime(getString(object, "")));
        }
    }

    /**
     * 如不能解析(yyyy-MM-dd)返回null
     **/
    public static Date getDate(String dateString) {
        try {
            if (StringUtils.length(dateString) != 10) {
                throw new Exception("Unparseable date: \"" + dateString + "\" size need 10!");
            } else {
                synchronized (lock) {
                    return DF_DATE.parse(dateString);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 如不能解析(yyyy-MM-dd HH:mm:ss)返回 null
     **/
    public static Date getDateTime(String dateString) {
        try {
            if (StringUtils.length(dateString) != 19) {
                throw new Exception("Unparseable date: \"" + dateString + "\" size need 19!");
            } else {
                synchronized (lock) {
                    return DF_DATE_TIME.parse(dateString);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static boolean isEmpty(Object o) {
        if (null == o) {
            return true;
        } else {
            if (o instanceof String) {
                return ((String) o).isEmpty();
            } else if (o instanceof Collection) {
                return ((Collection<?>) o).isEmpty();
            } else if (o instanceof Map) {
                return ((Map<?, ?>) o).isEmpty();
            } else if (o.getClass().isArray()) {
                return 0 == Array.getLength(o);
            } else if (o instanceof Iterator) {
                return !((Iterator<?>) o).hasNext();
            } else if (o instanceof Enumeration) {
                return !((Enumeration<?>) o).hasMoreElements();
            }
            return false;
        }
    }

    public static int countTrue(boolean... conditions) {
        int c = 0;
        for (boolean condition : conditions) {
            c += condition ? 1 : 0;
        }
        return c;
    }

    public static int countFalse(boolean... conditions) {
        int c = 0;
        for (boolean condition : conditions) {
            c += condition ? 0 : 1;
        }
        return c;
    }

    /**
     * 增加对象相等比较，如果对象不同时为空，类型要一致才相等
     */
    public static boolean equals(Object object1, Object object2) {
        if (object1 == object2) {
            return true;
        }
        if ((object1 == null) || (object2 == null)) {
            return false;
        }
        return object1.equals(object2);
    }

    public static boolean notEqual(Object object1, Object object2) {
        return equals(object1, object2) == false;
    }

    public static boolean notEqualString(Object object1, Object object2) {
        return equals(object1, object2) == false;
    }

    /**
     * 增加对象相等比较，如果对象不同时为空，类型要一致才相等
     */
    public static boolean equalString(Object object1, Object object2) {
        if (object1 == object2) {
            return true;
        }
        if ((object1 == null) || (object2 == null)) {
            return false;
        }
        return object1.toString().equals(object2.toString());
    }

}
