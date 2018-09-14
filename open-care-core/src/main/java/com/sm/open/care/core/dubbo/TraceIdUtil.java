package com.sm.open.care.core.dubbo;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @ClassName: TraceIdUtil
 * @Description: 生成traceid, 用于跟踪分布式服务的调用轨迹，任何要用traceid的地方，用此工具类获取
 * @Author yangtongbin
 * @Date 2018/9/14 10:06
 */
public class TraceIdUtil {

    private static ThreadLocal<String> localTraceid = new ThreadLocal<>();

    /**
     * Gets local traceid.
     *
     * @return the local traceid
     */
    public static String getLocalTraceid() {
        if (StringUtils.isEmpty(localTraceid.get())) {
            setLocalTraceid(getUUID());
        }
        return localTraceid.get();
    }

    /**
     * Sets local traceid.
     *
     * @param traceId the trace id
     */
    public static void setLocalTraceid(String traceId) {
        localTraceid.set(traceId);
    }

    /**
     * Clear local traceid.
     */
    public static void clearLocalTraceid(){
        localTraceid.remove();
    }

    /**
     * Get uuid string.
     *
     * @return the string
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
