package com.sm.open.care.core.utils;

import com.sm.open.care.core.enums.ValidType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 功能参数校验类
 * @ClassName: FunctionParamValid
 */
public class FunctionParamValid {
	
	private Map<String, ValidType> validParams = new HashMap<String,ValidType>();
	
	private FunctionParamValid() {
	}
	 
	private FunctionParamValid(String paramName, ValidType validType ) {
		validParams.put(paramName, validType);
	}
	
	public static FunctionParamValid create() {
		return new FunctionParamValid();
	}
	
	public static FunctionParamValid create(String paramName, ValidType validType ) {
		return new FunctionParamValid(paramName, validType);
	}
	
	public FunctionParamValid addValid(String paramName, ValidType validType) {
		validParams.put(paramName, validType);
		return this;
	}
	
	public FunctionParamValid removeValid(String paramName) {
		validParams.remove(paramName);
		return this;
	}
	
	public Map<String, ValidType> getValidParams() {
		return validParams;
	}
	
	public static final ValidType getValidType(FunctionParamValid fpv, String paramName) {
		if(fpv == null) {
			return null;
		} else {
			if(StringUtils.isNotBlank(paramName)) {
				return fpv.validParams.get(paramName);
			}
		}
		return null;
	}

	public ValidType getValidType(String paramName) {
		if(StringUtils.isNotBlank(paramName)) {
			return validParams.get(paramName);
		}
		return null;
	}
	
	public void clearValid() {
		validParams.clear();;
	}
	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE); 
	} 

}
