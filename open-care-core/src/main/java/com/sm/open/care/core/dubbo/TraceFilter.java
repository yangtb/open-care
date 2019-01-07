package com.sm.open.care.core.dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: TraceFilter
 * @Description: 跟踪分布式dubbo服务的调用轨迹
 * @Author yangtongbin
 * @Date 2018/9/14 10:09
 */
@Activate(group = {Constants.CONSUMER, Constants.PROVIDER})
public class TraceFilter implements Filter {

    private static Logger LOGGER = LoggerFactory.getLogger(TraceFilter.class);

    /**
     * dubbo存储traceId的key
     */
    private final String TRACE_KEY = "dubbo.traceId";

    /**
     * 特殊接口方法名
     */
    private static List<String> NOT_PRINT_METHOD = Arrays.asList("updatePsw");

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation)
            throws RpcException {

        RpcContext rpcContext = RpcContext.getContext();
        String traceid = rpcContext.getAttachment(TRACE_KEY);
        if (StringUtils.isEmpty(traceid)) {
            traceid = TraceIdUtil.getLocalTraceid();
            rpcContext.setAttachment(TRACE_KEY, traceid);
            // 将traceid放入工具类
            TraceIdUtil.setLocalTraceid(traceid);
        }

        String interfaceName = invoker.getInterface().getName();
        String methodName = invocation.getMethodName();
        InetSocketAddress remoteAddress = rpcContext.getRemoteAddress();
        String params = NOT_PRINT_METHOD.contains(methodName) ? "not print params" : JSON.toJSONString(invocation.getArguments());
        String remoteIpAndPort = remoteAddress.getHostString() + ":" + remoteAddress.getPort();
        boolean isConsumer = rpcContext.isConsumerSide();
        if (isConsumer) {
            LOGGER.info(String.format("traceId[%s]: calling [%s] %s.%s, params: %s", traceid,
                    remoteIpAndPort, interfaceName, methodName, params));
        } else {
            // 服务提供者新开了线程，重新set
            TraceIdUtil.setLocalTraceid(rpcContext.getAttachment(TRACE_KEY));
            LOGGER.info(String.format("traceId[%s]: %s.%s  is called by [%s], params: %s",
                    traceid, interfaceName, methodName, remoteIpAndPort, params));
        }

        // 开始时间
        long start = System.currentTimeMillis();

        try {
            return invoker.invoke(invocation);
        } finally {
            // 结束时间
            long end = System.currentTimeMillis();
            if (isConsumer) {
                LOGGER.info(String.format("traceId[%s]: calling %s.%s finish, timeCost: [%s]ms !",
                        traceid, interfaceName, methodName, end - start));
            } else {
                LOGGER.info(String.format("traceId[%s]: %s.%s is called finish, timeCost: [%s]ms !",
                        traceid, interfaceName, methodName, end - start));
            }
            TraceIdUtil.clearLocalTraceid();
        }

    }

}
