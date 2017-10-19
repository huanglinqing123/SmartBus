package com.example.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import Weizhang.Dingdan;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarttransportation.R;

public class Dingdanzhifu extends Activity {
	

	private EditText editText1,editText2,editText3,editText4,editText5;
	//private TextView text1,text2,text3,text4;
	private ListView listView;
	String[] files = new String[1021];//需要提交的证件
	int[] orderId = new int[1021];//订单号
	String[] userOrderId = new String[1021];//用户自定义单号
	double[] total = new double[1021];
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String json = (String) msg.obj;
			try { 
				JSONObject jsonObject = new JSONObject(json);
				String code = jsonObject.getString("reason");
				if (code.equals("success")) {
					Toast.makeText(Dingdanzhifu.this, "查询成功", 0).show();
					org.json.JSONArray array = jsonObject
							.getJSONArray("result");
					for (int i = 0; i < array.length(); i++) {
						JSONObject jsonObject2 = array.getJSONObject(i);
						files[i] = jsonObject2.getString("files");
						orderId[i] = jsonObject2.getInt("orderId");
						userOrderId[i] = jsonObject2.getString("userOrderId");
						total[i] = jsonObject2.getDouble("total");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("files", files[i]);
						map.put("orderId", orderId[i]);
						map.put("userOrderId", userOrderId[i]);
 					    map.put("total", total[i]);
						list.add(map);
					}
					SimpleAdapter adapter = new SimpleAdapter(Dingdanzhifu.this,
							list, R.layout.zhengjianshangchuan, new String[] {
									"files", "orderId", "userOrderId", "total"
									}, new int[] {
									R.id.files, R.id.orderId,R.id.userId,R.id.total
									});
					listView.setAdapter(adapter);
				} else {
					Toast.makeText(Dingdanzhifu.this, "查询失败", 0).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dingdanzhifu);
		Intent intent=getIntent();
		int id=intent.getIntExtra("recordsId", 0);
		//Toast.makeText(Dingdanzhifu.thi11s, id, 0).show();
		String carNo = intent.getStringExtra("carNo");
		listView = (ListView) findViewById(R.id.zhifu_list);
		editText1=(EditText) findViewById(R.id.id);
		editText2=(EditText) findViewById(R.id.carNo);
		editText3=(EditText) findViewById(R.id.contactName);
		editText4=(EditText) findViewById(R.id.tel);
		editText5=(EditText) findViewById(R.id.userOrderId);
//		text1 = (TextView) findViewById(R.id.files);
//		text2 = (TextView) findViewById(R.id.orderId);
//		text3 = (TextView) findViewById(R.id.userId);
//		text4 = (TextView) findViewById(R.id.total);
		editText1.setText(id+"");
		editText2.setText(carNo);
	}
	public void tijiao(View v){
		String fadanhao = editText1.getText().toString();
		String chepai = editText2.getText().toString();
		String lianxiren = editText3.getText().toString();
		String haoma = editText4.getText().toString();
		String zidingyi = editText5.getText().toString();
		Dingdan.getRequest2(handler, fadanhao, lianxiren, haoma, zidingyi, chepai);
	}
	public void zhifu(View v){
		
	}

}

