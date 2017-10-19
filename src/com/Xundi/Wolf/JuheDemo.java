package com.Xundi.Wolf;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.os.Message;

import net.sf.json.JSONObject;

/**
 * 
 **/

public class JuheDemo {
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	// 配置您申请的KEY
	public static final String APPKEY = "ea6e8ccc855a5978d26ce21d96a607fb";
	
	// 2.公交线路查询接口
	public static void getRequest2(final Handler handler, final String city,
			final String q) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = null;
				String url = "http://op.juhe.cn/189/bus/busline";// 请求接口地址
				Map params = new HashMap();// 请求参数
				params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)
				//返回数据格式
				params.put("city", city);// 需要查询的城市，所有支持的城市由接口1提供。
				params.put("q", q);// 待查询的线路，如： 110，快线1号

				try {
					result = net(url, params, "GET");
					Message message = new Message();
					message.obj = result.toString();
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}).start();
		
	}

	// 3.站台查询接口
	public static void getRequest3(final Handler handler, final String city,
			final String q) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = null;
				String url = "http://op.juhe.cn/189/bus/station";// 请求接口地址
				Map params = new HashMap();// 请求参数
				params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)
				params.put("city", city);// 需要查询的城市，所有支持的城市由接口1提供。
				params.put("station", q);// 待查询的站台名称，如： 西直门

				try {
					result = net(url, params, "GET");
					Message message = new Message();
					message.obj = result.toString();
					message.what = 2;
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	// 4.公交换乘接口
	public static void getRequest4() {
		String result = null;
		String url = "http://op.juhe.cn/189/bus/transfer";// 请求接口地址
		Map params = new HashMap();// 请求参数
		params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)
		params.put("city", "");// 需要查询的城市，所有支持的城市由接口1提供。
		params.put("start_lat", "");// 起点纬度
		params.put("start_lng", "");// 起点经度
		params.put("end_lat", "");// 终点纬度
		params.put("end_lng", "");// 终点经度
		params.put("rc", "");// 换乘偏好 0：综合排序 1：换乘次数 2：步行距离 3：时间 4：距离 5：地铁优先 默认：0

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				System.out.println(object.get("result"));
			} else {
				System.out.println(object.get("error_code") + ":"
						+ object.get("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 5.周边公交站台接口
	public static void getRequest5() {
		String result = null;
		
		String url = "http://api.juheapi.com/bus/nearby";// 请求接口地址
		Map params = new HashMap();// 请求参数
		params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)
		params.put("city", "");// 需要查询的城市，所有支持的城市由接口1提供。
		params.put("lat", "");// 起点纬度
		params.put("lng", "");// 起点经度
		params.put("dist", "");// 查询半径

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				System.out.println(object.get("result"));
			} else {
				System.out.println(object.get("error_code") + ":"
						+ object.get("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param strUrl
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param method
	 *            请求方法
	 * @return 网络请求字符串
	 * @throws Exception
	 */
	public static String net(String strUrl, Map params, String method)
			throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if (method == null || method.equals("GET")) {
				strUrl = strUrl + "?" + urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if (method == null || method.equals("GET")) {
				conn.setRequestMethod("GET");
			} else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-" +
					"", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params != null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(
							conn.getOutputStream());
					out.writeBytes(urlencode(params));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	// 将map型转为请求参数型
	public static String urlencode(Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=")
						.append(URLEncoder.encode(i.getValue() + "", "UTF-8"))
						.append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
