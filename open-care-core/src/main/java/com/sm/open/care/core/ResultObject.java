package com.sm.open.care.core;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @ClassName: ResultObject
 * @Description:
 * @Author yangtongbin
 * @Date 2017/9/14 10:41
 */
public class ResultObject implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String SUCCESS_CODE = "0";
    public static final String MSG_SUCCESS = "success";
    public static final String METHOD_NAME_EMPTY = "method_name_empty";

    /**
     * data 中类型为普通 Map&lt;String,Object>对象
     */
    public static final int DATA_TYPE_MAP = 0;
    /**
     * <p>
     * data 中类型为普通 集合列表对象 List&lt;Map&lt;String,Object>>
     * </p>
     */
    public static final int DATA_TYPE_LIST = 1;
    /**
     * data 中类型为普通 javabean 单个对象
     */
    public static final int DATA_TYPE_OBJECT = 2;

    private String methodName = "method_name_empty";
    private String code = SUCCESS_CODE;
    private String msg;
    private int dataType = 0;

    private Object data;


    public static final ResultObject create(Object data) {
        return new ResultObject(METHOD_NAME_EMPTY, SUCCESS_CODE, "", 0, data);
    }

    public static final ResultObject create(String code, Object data) {
        return new ResultObject(METHOD_NAME_EMPTY, code, "", 0, data);
    }

    public static final ResultObject create(String methodName, String code, Object data) {
        return new ResultObject(methodName, code, "", 0, data);
    }

    public static final ResultObject create(String methodName, String code, String msg, Object data) {
        return new ResultObject(methodName, code, msg, 0, data);
    }

    public static final ResultObject create(String methodName, String code, String msg, int dataType, Object data) {
        return new ResultObject(methodName, code, msg, dataType, data);
    }

    public static final ResultObject createSuccess(String methodName, int dataType, Object data) {
        return new ResultObject(methodName, SUCCESS_CODE, MSG_SUCCESS, dataType, data);
    }

    public static final ResultObject create(String methodName, String code, String msg) {
        return new ResultObject(methodName, code, msg, 0, null);
    }

    public ResultObject(String methodName, String code, String msg, int dataType, Object data) {
        this.methodName = methodName;
        this.code = code;
        this.msg = msg;
        this.dataType = dataType;
        this.data = data;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return (SUCCESS_CODE.equals(code));
    }

    public boolean isFail() {
        return (!isSuccess());
    }

    public boolean isListData() {
        return (DATA_TYPE_LIST == dataType);
    }

    public boolean isEmptyData() {
        if (null == data) {
            return true;
        } else {
            if (data instanceof Collection) {
                return ((Collection<?>) data).isEmpty();
            } else if (data instanceof Map) {
                return ((Map<?, ?>) data).isEmpty();
            } else if (data.getClass().isArray()) {
                return 0 == Array.getLength(data);
            } else if (data instanceof Iterator) {
                return !((Iterator<?>) data).hasNext();
            } else if (data instanceof Enumeration) {
                return !((Enumeration<?>) data).hasMoreElements();
            }
            return false;
        }
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Map<String, Object> result() {
        Map<String, Object> response = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("code", code);
        dataMap.put("msg", msg);
        if (dataType == 0) {
            if (data instanceof Map) {
                dataMap.putAll((Map) this.data);
            } else {
                dataMap.put("data", this.data);
            }
        } else if (dataType == 1) {
            if (data instanceof List) {
                dataMap.put("data_list", this.data);
            }
        } else {
            dataMap.put("data", this.data);
        }

        response.put(this.methodName + "_response", dataMap);
        return response;
    }


    public static void main(String[] args) {

    }
}