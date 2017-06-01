package com.sm.open.care.core.utils;

import com.alibaba.fastjson.JSON;
import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.log.LoggerUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: bean相关工具类
 */
public class BeanUtil {

	private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	/**
	 * @Description: 将 T 类型的对象数据转换成 V 类型数据
	 * @Param:
	 * @Return:
	 */
	public static <T, V> V convert(T t, Class<V> clazz) {
		if (t == null) {
			return null;
		}
		try {
			V v = clazz.newInstance();
			BeanUtils.copyProperties(t, v);
			return v;
		} catch (Exception e) {
			LoggerUtil.info(logger, "【core-BeanUtils-convert】, {0}, t:{1}",
					MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110008, t.getClass(), clazz), JSON.toJSONString(t));
			throw new BizRuntimeException(ErrorCode.ERROR_GENERAL_110008,
					MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110008, t.getClass(), clazz), e);
		}
	}

	/**
	 * @Description: 将 T 类型的数据对象数组转换成 V 类型对象的数据数组
	 * @Param:
	 * @Return:
	 */
	public static <T, V> List<V> convertList(List<T> tList, Class<V> clazz) {
		if(CollectionUtils.isEmpty(tList)) {
			return null;
		}
		List<V> vList = new ArrayList<V>(tList.size());
		T t = null;
		for(int i = 0; i < tList.size(); i ++) {
			t = tList.get(i);
			vList.add(BeanUtil.convert(t, clazz));
		}
		return vList;
	}
}
