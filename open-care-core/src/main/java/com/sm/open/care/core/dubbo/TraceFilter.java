package com.sm.open.care.core.dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

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
     * dubbo存储traceid的key
     */
    private final String TRACE_KEY = "dubbo.traceid";

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
        String remoteIpAndPort = remoteAddress.getHostString() + ":"
                + remoteAddress.getPort();
        boolean isConsumer = rpcContext.isConsumerSide();
        if (isConsumer) {
            LOGGER.info(String.format("Traceid[%s]: calling [%s] %s.%s", traceid,
                    remoteIpAndPort, interfaceName, methodName));
        } else {
            // 服务提供者新开了线程，重新set
            TraceIdUtil.setLocalTraceid(rpcContext.getAttachment(TRACE_KEY));
            LOGGER.info(String.format("Traceid[%s]: %s.%s  is called by [%s]",
                    traceid, interfaceName, methodName, remoteIpAndPort));
        }

        try {
            Result r = invoker.invoke(invocation);
            return r;
        } finally {
            if (isConsumer) {
                LOGGER.info(String.format("Traceid[%s]: calling %s.%s finish !",
                        traceid, interfaceName, methodName));
            } else {
                LOGGER.info(String.format("Traceid[%s]: %s.%s is called finish !",
                        traceid, interfaceName, methodName));
            }
            TraceIdUtil.clearLocalTraceid();
        }

    }

}
