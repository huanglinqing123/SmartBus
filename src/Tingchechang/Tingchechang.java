package Tingchechang;

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

public class Tingchechang {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
 
   //appkey
    public static final String APPKEY ="d6c7612138791a5364e288073957f5ee";
 
    
 
   
   //请求访问api接口
    public static void getRequest3(final Handler handler,final Float jd,final Float wd){
    	new Thread(new Runnable() {
			//网络访问需要使用线程，防止阻塞
			@Override
			public void run() {
				// TODO Auto-generated method stub
				  String result =null;
			      //请求接口地址
			        String url ="http://japi.juhe.cn/park/nearPark.from";
			           //请求参数
			            Map params = new HashMap();
			            //应用APPKEY(应用详细页查询)
			            params.put("key",APPKEY);
			            params.put("SDXX", 1);
			            //经度
			            params.put("JD",jd);
			            //纬度
			            params.put("WD",wd);
			 
			        try {
			            result =net(url, params, "GET");
			            Message message=new Message();
			            message.obj=result.toString();
			            handler.sendMessage(message);
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			}
		}).start();
      
    }
    public static void getRequest1(final Handler handler,final String id){
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				  String result =null;
			        String url ="http://japi.juhe.cn/park/baseInfo.from";//请求接口地址
			        Map params = new HashMap();//请求参数
			            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
			            params.put("CCID", id);
			 
			        try {
			            result =net(url, params, "GET");
			            Message message=new Message();
			            message.obj=result.toString();
			            handler.sendMessage(message);
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			}
		}).start();
      
    }
 
 
 
 
 
    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    //网络请求算法
    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
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
 
    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
