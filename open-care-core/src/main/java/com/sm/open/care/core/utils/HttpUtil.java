package com.sm.open.care.core.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtil {
	private static HttpClient httpClient;

	/**
	 * 获取webcontext完整路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getBasePath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath();
	}

	/**
	 * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 * 
	 * @param URL
	 *            url地址
	 * @return url请求参数部分
	 */
	public static Map<String, String> urlRequest(String URL) {
		Map<String, String> mapRequest = new HashMap<String, String>();

		String[] arrSplit = null;

		String strUrlParam = truncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		// 每个键值为一组 www.2cto.com
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");

			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}

	/**
	 * 去掉url中的路径，留下请求参数部分
	 * 
	 * @param strURL
	 *            url地址
	 * @return url请求参数部分
	 */
	private static String truncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;

		strURL = strURL.trim().toLowerCase();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1 && arrSplit.length > 1 && arrSplit[1] != null) {
					strAllParam = arrSplit[1];
		}

		return strAllParam;
	}

	public static String postForJson(String url, String json)
			throws ClientProtocolException, IOException {
		// log.debug("====>http请求-->请求地址:"+url);
		String resultJson = "";
		httpClient = HttpClientBuilder.create().build();
		// 请求头
		StringEntity entity = new StringEntity(json);
		entity.setContentType("application/json");
		entity.setContentEncoding("utf-8");
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(entity);
		httpPost.addHeader("Accept", "application/json");
		// 发起请求
		HttpResponse response = httpClient.execute(httpPost);
		int httpStatus = response.getStatusLine().getStatusCode();
		if (httpStatus != 200){
			return resultJson;
		}
		// //log.debug("====>http请求-->返回状态:"+
		// response.getStatusLine().getReasonPhrase());
		// 结果转换成json
		HttpEntity httpEntity = response.getEntity();
		httpEntity.getContent();
		resultJson = EntityUtils.toString(response.getEntity());
		// //log.debug("====>http请求-->返回字符串:"+resultJson);
		return resultJson;
	}

	public static String getForJson(String url) throws IOException {
		// log.debug("====>http请求-->请求地址:" + url);
		String resultJson = "";
		httpClient = HttpClientBuilder.create().build();
		// 请求头
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Accept", "application/json");
		// 发起请求
		HttpResponse response = httpClient.execute(httpGet);
		int httpStatus = response.getStatusLine().getStatusCode();
		if (httpStatus != 200){
			return resultJson;
		}
		// log.debug("====>http请求-->返回状态:"+
		// response.getStatusLine().getReasonPhrase());
		// 结果转换成json
		HttpEntity httpEntity = response.getEntity();
		httpEntity.getContent();
		resultJson = EntityUtils.toString(response.getEntity());
		// log.debug("====>http请求-->返回字符串:"+resultJson);
		return resultJson;
	}
	/**
	 * @Description:测试url是否有效
	 */
	public static boolean isEffective(String url) throws IOException {
		httpClient = HttpClientBuilder.create().build();
		// 请求头
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Accept", "application/json");
		// 发起请求
		try {
			HttpResponse response = httpClient.execute(httpGet);
			int httpStatus = response.getStatusLine().getStatusCode();
			if (httpStatus >= 400){
                return false;
            }
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 校验请求地址白名单【免拦截】。
	 *
	 * @param url
	 * @return
	 */
	public static boolean isPassAuthPath(List<String> exceptPathEqual,List<String> exceptPathIndex, String url) {
		// 1:白名单URL地址不再鉴权操作(准确匹配)。
		if (exceptPathEqual.contains(url)) {
			return true;
		}
		// 2:白名单URL地址不再鉴权操作(模糊匹配)。
		for (int i = 0; i < exceptPathIndex.size(); i++) {
			if(url.indexOf(exceptPathIndex.get(i)) >= 0){
				return true;
			}
		}
		return false;
	}

	/**
	 * 拼接系统域名地址
	 *
	 * @param request
	 */
	public static String schemeWithSys(HttpServletRequest request,String httpConfig, String system) {
		// 此处使用环境变量解决请求https被动态获取丢失问题。
		return httpConfig + request.getServerName() + "/" + system;
	}

}
