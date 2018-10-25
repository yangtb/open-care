package com.sm.open.care.core.log;

import java.util.UUID;

/**
 *
 */
public class LogTrace {
    private String traceId;				// 追踪上下游调用的 id, 上下线程ID
    private String appId;				// appId
    private String isvAppId;			// isvAppId
    private String bizType;				// 业务类型
    private String resultStatus;		// 结果状态，T为成功， F 为失败， E 系统异常
    private String errorCode = "-";		// 错误码，当resultStatus=F或者E时才有错误码，如果resultStatus=T，则 errorCode打印为 "-"
    private String errorMsg = "-";		// 错误信息，当resultStatus=F或者E时才有错误信息，如果resultStatus=T，则 errorMsg打印为 "-"
    private Long timeCost;

    private Object requestParams;
    private Object responseResult;

    public final static String RESULT_STATUS_T = "T";	// 成功
    public final static String RESULT_STATUS_F = "F";	// 失败
    public final static String RESULT_STATUS_E = "E";	// 异常

    private LogTrace(){}

    public String getTraceId() {
        return traceId;
    }

    public LogTrace setTraceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public LogTrace setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public LogTrace setIsvAppId(String isvAppId) {
        this.isvAppId = isvAppId;
        return this;
    }

    public LogTrace setBizType(String bizType) {
        this.bizType = bizType;
        return this;
    }

    public LogTrace setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
        return this;
    }

    public LogTrace setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public LogTrace setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public LogTrace setTimeCost(Long timeCost) {
        this.timeCost = timeCost;
        return this;
    }

    public LogTrace setRequestParams(Object requestParams) {
        this.requestParams = requestParams;
        return this;
    }

    public LogTrace setResponseResult(Object responseResult) {
        this.responseResult = responseResult;
        return this;
    }

    public final static LogTrace createLogTrace(String appId, String isvAppId, String bizType) {
        return new LogTrace()
                .setTraceId(UUID.randomUUID().toString().replace("-", ""))
                .setAppId(appId)
                .setIsvAppId(isvAppId)
                .setBizType(bizType);
    }

    public final static LogTrace createLogTrace(String appId, String isvAppId, String bizType, Long timeCost, Object requestParams, Object responseResult) {
        return new LogTrace()
                .setTraceId(UUID.randomUUID().toString().replace("-", ""))
                .setAppId(appId)
                .setIsvAppId(isvAppId)
                .setBizType(bizType)
                .setTimeCost(timeCost)
                .setRequestParams(requestParams)
                .setResponseResult(responseResult);
    }

    public String build() {
        return traceId + ", "
                + bizType + ", " + resultStatus + ", " + errorCode + ", "
                + errorMsg + ", " + timeCost + "ms, " + (requestParams == null ? "(-)" : requestParams) + ", " + (responseResult == null ? "(-)" : responseResult);
    }

}
