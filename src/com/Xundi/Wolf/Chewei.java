package com.Xundi.Wolf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Tingchechang.Tingchechang;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.smarttransportation.R;

public class Chewei extends Activity {

	private AutoCompleteTextView autoCompleteTextView;
	private ListView listView;
	final String[] KCWZT = new String[2000];
	final String CCMC[] = new String[2000];
	final String CCDZ[] = new String[2000];
	final String CCID[] = new String[2000];

	private List<Map<String, Object>> dates = new ArrayList<Map<String, Object>>();
	private LocationManager locationManager;
	private ArrayAdapter adapter;
	private String chengshi[] = { "沈阳市", "乌鲁木齐市", "长沙市", "重庆市", "常熟市", "郑州市",
			"温州市", "厦门市", "成都市", "武汉市", "西安市", "青岛市", "济南市", "福州市", "常州市",
			"苏州市", "南京市", "杭州市", "深圳市", "上海市", "天津市", "广州市", "北京市" };
	List<String> list=Arrays.asList(chengshi);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chewei);
		autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		listView = (ListView) findViewById(R.id.listView1);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, chengshi);
		//将支持的城市信息绑定到自动补全输入框
		autoCompleteTextView.setAdapter(adapter);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


	}
    //通过一个handler线程接收返回数据
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			//获取json格式的字符串
			String jsondata = (String) msg.obj;
			StringBuffer buffer = new StringBuffer();
			try {
				JSONObject jsonObject = new JSONObject(jsondata);
				//判断是否查询成功
				if (jsonObject.getInt("error_code") == 0) {
					Toast.makeText(Chewei.this, "查询成功", 0).show();
					JSONArray array = new JSONArray(
							jsonObject.getString("result"));
					for (int i = 0; i < array.length(); i++) {
						JSONObject jsonObject2 = array.getJSONObject(i);
						 CCID[i]=jsonObject2.getString("CCID");
						 KCWZT[i] = jsonObject2.getString("KCWZT");
						String jieguo = "";
						//判断车位状况
						if (KCWZT[i].equals("P1001.png")) {
							jieguo = "车位充足";
						} else if (KCWZT[i].equals("P1002.png")) {
							jieguo = "车位够用";
						} else if (KCWZT[i].equals("P1003.png")) {
							jieguo = "车位较少";
						} else if (KCWZT[i].equals("P1004.png")) {
							jieguo = "车位紧张";
						} else if (KCWZT[i].equals("P1005.png")) {
							jieguo = "车位未知";
						} else if (KCWZT[i].equals("P1006.png")) {
							jieguo = "车位关闭";
						}
						CCMC[i] = jsonObject2.getString("CCMC");
						CCDZ[i] = jsonObject2.getString("CCDZ");
						//装入数据
						Map<String, Object> item = new HashMap<String, Object>();
						item.put("ccdz", CCDZ[i]);
						item.put("ccmc", CCMC[i]);
						item.put("jieguo", jieguo);
						dates.add(item);
					}
					//将数据绑定
					SimpleAdapter adapter1 = new SimpleAdapter(Chewei.this,
							dates, R.layout.cheweixinxi, new String[] { "ccmc",
									"ccdz", "jieguo" }, new int[] {
									R.id.textView1, R.id.textView2,
									R.id.textView3 });
					listView.setAdapter(adapter1);

				} else {
					Toast.makeText(Chewei.this, "查询失败", 0).show();
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};
//
//	 @Override
//	 public boolean onCreateOptionsMenu(Menu menu) {
//	 // Inflate the menu; this adds items to the action bar if it is present.
//	 getMenuInflater().inflate(R.menu.chewei, menu);
//	 return true;
//	 }
	//查询按钮监听事件
	public void huang(View v) {
		// Location location = locationManager
		// .getLastKnownLocation(LocationManager.GPS_PROVIDER);

		// Tingchechang.getRequest2(handler, location.getLongitude(),
		// location.getLatitude());
		//再次传入的参数是所在地的经纬度，这里我们一北京为例，正常使用是通过上述代码获取当地经纬度
	    //但本地是滁州，api接口并不支持本城市。
		Tingchechang.getRequest3(handler, 116.4071136987f, 39.9046363143f);
		

	}

}
