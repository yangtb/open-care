package com.sm.open.care.core.utils;

import net.sf.cglib.beans.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Description:
 * @ClassName: MapUtil.java
 */
public class MapUtil {
	
	private static final Logger logger = Logger.getLogger(MapUtil.class);

	private MapUtil() {

	}
	public static String getString(final Map<String, Object> map, String key, String defaultValue) {
		if (map == null) {
			return defaultValue;
		}
		return ValueUtil.getString(map.get(key), defaultValue);
	}

	public static String getString(final Map<String, Object> map, String key) {
		return ValueUtil.getString(map.get(key));
	}

	public static int getInt(final Map<String, Object> map, String key, int defaultValue) {
		if (map == null) {
			return defaultValue;
		}
		return ValueUtil.getInt(map.get(key), defaultValue);
	}

	public static int getInt(final Map<String, Object> map, String key) {
		return ValueUtil.getInt(map.get(key));
	}

	public static long getLong(final Map<String, Object> map, String key, long defaultValue) {
		if (map == null) {
			return defaultValue;
		}
		return ValueUtil.getLong(map.get(key), defaultValue);
	}

	public static long getLong(final Map<String, Object> map, String key) {
		return ValueUtil.getLong(map.get(key));
	}

	public static double getDouble(final Map<String, Object> map, String key, double defaultValue) {
		if (map == null) {
			return defaultValue;
		}
		return ValueUtil.getDouble(map.get(key), defaultValue);
	}

	public static double getDouble(final Map<String, Object> map, String key) {
		return ValueUtil.getDouble(map.get(key));
	}

	public static boolean getBoolean(final Map<String, Object> map, String key, boolean defaultValue) {
		if (map == null) {
			return defaultValue;
		}
		return ValueUtil.getBoolean(map.get(key), defaultValue);
	}

	public static boolean getBoolean(final Map<String, Object> map, String key) {
		return ValueUtil.getBoolean(map.get(key));
	}

	public static Boolean getBooleanObj(final Map<String, Object> map, String key, Boolean defaultValue) {
		if (map == null) {
			return defaultValue;
		}
		return ValueUtil.getBooleanObj(map.get(key), defaultValue);
	}

	public static Boolean getBooleanObj(final Map<String, Object> map, String key) {
		return ValueUtil.getBooleanObj(map.get(key));
	}

	public static Date getDate(final Map<String, Object> map, String key) {
		return ValueUtil.getDate(map.get(key));
	}

	public static Date getDateTime(final Map<String, Object> map, String key) {
		return ValueUtil.getDateTime(map.get(key));
	}
	/** 将 bean 转化为 Map */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static Map<String, Object> objectToMap(Object bean) {
		if(bean == null) {
			return null;
		}
		try {
			Class type = bean.getClass();
			Map resMap = new HashMap();
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (result != null) {
						resMap.put(propertyName, result);
					} else {
						resMap.put(propertyName, "");
					}
				}
			}
			return resMap;
		} catch (Exception e) {
			logger.error("toMap error: ", e);
		}
		return null;
	}

	public static Map<String,Object> objectToMapByFields(Object bean,String[] fields) {
		try {
			Class<?> type = bean.getClass();
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			Map<String,Object> resMap = new HashMap<String,Object>();
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				for (int j = 0;j<fields.length;j++) {
					String field = fields[j];
					if (propertyName.equals(field)) {
						Method readMethod = descriptor.getReadMethod();
						Object result = readMethod.invoke(bean, new Object[0]);
						if (result != null) {
							resMap.put(propertyName, result);
						} else {
							resMap.put(propertyName, "");
						}
					}
				}
			}
			return resMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 将 Map 转化为 bean */
	@SuppressWarnings("unchecked")
	public static <T> T mapToObject(Map<String, Object> map, Class<T> type) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
			Object obj = type.newInstance(); // 创建 JavaBean 对象
			// 给 JavaBean 对象的属性赋值
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (map.containsKey(propertyName)) {
					// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
					Object value = map.get(propertyName);
					Object[] args = new Object[1];
					args[0] = value;
					descriptor.getWriteMethod().invoke(obj, args);
				}
			}
			return (T) obj;
		} catch (Exception e) {
			logger.error("mapToObject error: ", e);
		}
		return null;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static Map<String, Object> getMap(final Map<String, Object> map, String key) {
		try {
			Object value = map.get(key);
			if(value == null) {
				return null;
			}
			if (value instanceof Map) {
				return (Map<String, Object>) value;
			} else if (value instanceof List) {
				List list = (List) value;
				if (!list.isEmpty()) {
					Object element = list.get(0);
					if (element instanceof Map) {
						return (Map<String, Object>) element;
					}
				}
			}
		} catch (Exception e) {
			logger.error("getMap error", e);
		}
		return null;
	}

	/** 将 Map中值为空的对象移除 */
	public static Map<String, Object> removeNullValue(final Map<String, Object> map) {
		if (map == null) {
			return null;
		} else {
			Map<String, Object> resMap = new HashMap<String, Object>();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (null != entry.getValue()) {
					resMap.put(entry.getKey(), entry.getValue());
				}
			}
			return resMap;
		}
	}
	
	public static Map<String, Object> convertMap(String key, Object object) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key,  object);
		return map;
	}
	
	public static Map<String, Object> convertMap(String key, Object object, Map<String, Object> params) {
		params.put(key,  object);
		return params;
	}
	/**
	 * 获取一个Map中所有的Keys对象生成一个新的子Map
	 * @param map 总的键值对
	 * @param keys 需要加入子集合的键
	 */
	public static Map<String, Object> subMap(final Map<String, Object> map, String... keys) {
		return subMap(map, false, keys);
	}
	/**
	 * 获取一个Map中所有的Keys对象生成一个新的子Map
	 * @param map 总的键
	 * @param filterNullVal 判断是否需要将key-value中value=null加入到子集合中
	 * @param keys 需要加入子集合的键
	 */
	public static Map<String, Object> subMap(final Map<String, Object> map, Boolean filterNullVal,String ...keys) {
		if(map == null || keys == null) {
			return null;
		} else {
			Map<String, Object> resMap = new HashMap<String, Object>();
			for (int i = 0, n = keys.length; i < n; i++) {
				if (filterNullVal) {
					if (null != map.get(keys[i])) {
						resMap.put(keys[i], map.get(keys[i]));
					}
				} else {
					resMap.put(keys[i], map.get(keys[i]));
				}
			}
			return resMap;
		}
	}
	
	/**
	 * 获取一个Map中所有的Keys对象生成一个新的子Map
	 * @param map 总的键
	 * @param filterNullVal 判断是否需要将key-value中value=null加入到子集合中
	 * @param keys 需要加入子集合的键
	 */
	public static Map<String, Object> subMap(final Map<String, Object> map, Boolean filterNullVal,List<String> keys) {
		if(map == null || keys == null) {
			return null;
		} else {
			Map<String, Object> resMap = new HashMap<String, Object>();
			for (int i = 0, n = keys.size(); i < n; i++) {
				if (filterNullVal) {
					if (null != map.get(keys.get(i))) {
						resMap.put(keys.get(i), map.get(keys.get(i)));
					}
				} else {
					resMap.put(keys.get(i), map.get(keys.get(i)));
				}
			}
			return resMap;
		}
	}
	
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hosId", 123456L);
		map.put("pcId", null);
		map.put("userId", "213334144");
		map.put("test1", "");
		map.put("userName", null);
		String[] keys = { "hosId", "pcId", "userId","ddd","test1" };
		System.out.println("subMap false>>>"+subMap(map,false, keys));
		System.out.println("subMap true>>>"+subMap(map,true, keys));
		System.out.println("old map ->"+map);
        System.out.println("convertMapString map ->" + convertMapString(map));
		System.out.println("removeNullValue map ->"+removeNullValue(map));
		
		System.out.println("old map ->" + map + " subMap->" + subMap(map,false, "hosId","userId","usName"));
		Map<String, Object> resMap = new HashMap<String, Object>();
		System.out.println("makeQueryString map ->" + makeQueryString(resMap));
	}
	
	public static Map<String, Object> convertMap(String key, Map<String, Object> params, String defaultValue) {
		Map<String, Object> result = new HashMap<String, Object>();
		return convertMap(key, MapUtil.getString(params, key, defaultValue), result);
	}
	
	public static Map<String, Object> mergeBehind(Map<String, Object> objectToMap, Map<String, Object> params) {
		objectToMap.putAll(params);
		params.clear();
		params.putAll(objectToMap);
		return params;
	}
	
	public static Map<String, Object> mergeFront(Map<String, Object> objectToMap, Map<String, Object> params) {
		params.putAll(objectToMap);
		return params;
	}

	/***
	 * 根据 map 生成类似参数字串： name1=value1&name2=value2
	 * @param map
	 * @return
     */
	public static String makeQueryString(final Map<String, Object> map) {
		if (map == null) {
			return "";
		} else {
			StringBuilder sb = new StringBuilder(100);
			List<String> keys = new ArrayList<String>(map.keySet());
			Collections.sort(keys);
			for(String key:keys){
				if (StringUtils.isBlank(MapUtil.getString(map, key))) {
					continue;
				}
				sb.append("&").append(key).append("=") .append(MapUtil.getString(map, key));
			}
			if(sb.length() > 0) {
				sb.deleteCharAt(0);
			}
			return sb.toString();
		}
	}
	
    public static Map<String, String> convertMapString( Map<String, Object> map) {
        Map<String, String> resMap = new HashMap<String, String>();
        if(map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                resMap.put(entry.getKey(),  ((entry.getValue()==null) ? null : entry.getValue().toString()));
            }
        }
        return resMap;
    }
    
	/**
	 * @Description: java bean 转换成 Map<String, String>
	 * @param bean
	 * @return
	 */
    public static <T> Map<String, String> beanToStringMap(T bean) {  
		Map<String, String> map = new HashMap<String, String>(); 
	    if (bean != null) {  
	        BeanMap beanMap = BeanMap.create(bean);  
	        for (Object key : beanMap.keySet()) {  
	            map.put(key + "", beanMap.get(key) + "");  
	        }             
	    }  
	    return map;  
	} 

	/**
	 * @Description: java bean 转换成 Map<String, Object>
	 * @param bean
	 * @return
	 */
	public static <T> Map<String, Object> beanToObjectMap(T bean) {  
		Map<String, Object> map = new HashMap<String, Object>(); 
	    if (bean != null) {  
	        BeanMap beanMap = BeanMap.create(bean);  
	        for (Object key : beanMap.keySet()) {  
	            map.put(key + "", beanMap.get(key));  
	        }             
	    }  
	    return map;  
	} 
    
}
