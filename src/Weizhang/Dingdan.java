package Weizhang;
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

public class Dingdan {
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	// 配置您申请的KEY
		public static final String APPKEY = "75af88c6d84bb663315adb2f76dc8370";
		public static void getRequest2(final Handler handler, final String recordId,
				final String userName,final String tel,final String ordernum,final String carNo) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					String result = null;
					String url = "http://v.juhe.cn/wzdj/submitOrder.php";// 请求接口地址
					Map params = new HashMap();// 请求参数
					params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)
					params.put("dtype", "");// 返回数据的格式,xml或json，默认json
					params.put("recordIds", recordId);//订单编号
					params.put("contactName", userName);//联系人
					params.put("tel",tel);//号码
					params.put("userOrderId",ordernum);//自定义号码
					params.put("carNo",carNo);//

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
				conn.setRequestProperty("User-agent", userAgent);
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
