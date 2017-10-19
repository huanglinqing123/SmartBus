package com.example.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import Weizhang.WZSe;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.example.smarttransportation.R;
public class WZSelect extends Activity {

	private ListView listView;
	private EditText editText1, editText2;
	String time[] = new String[1021];
	double fine[] = new double[1021];
	String cityname[] = new String[1021];
	String beha[] = new String[1021];
	int id[] = new int[1021];
	String[] carNo = new String[1021];
	int fen[] = new int[1021];
	String adress[] = new String[1021];
	String fuwufei[]=new String[1021];
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String json = (String) msg.obj;
			try { 
				JSONObject jsonObject = new JSONObject(json);
				String code = jsonObject.getString("reason");
				if (code.equals("success")) {
					Toast.makeText(WZSelect.this, "查询成功", 0).show();
					org.json.JSONArray array = jsonObject
							.getJSONArray("result");
					for (int i = 0; i < array.length(); i++) {
						JSONObject jsonObject2 = array.getJSONObject(i);
						time[i] = jsonObject2.getString("time");
						fine[i] = jsonObject2.getDouble("fine");
						cityname[i] = jsonObject2.getString("cityName");
						id[i] = jsonObject2.getInt("recordId");
						carNo[i] =jsonObject2.getString("carNo");
						beha[i] = jsonObject2.getString("behavior");
						fen[i] = jsonObject2.getInt("deductPoint");
						adress[i] = jsonObject2.getString("address");
						fuwufei[i]=jsonObject2.getString("serviceFee");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("time", time[i]);
						map.put("fine", fine[i]);
						map.put("cityname", cityname[i]);
						map.put("id", id[i]);
						map.put("beha", beha[i]);
						map.put("fen", fen[i]);
						map.put("adress", adress[i]);
						map.put("fuwufei", fuwufei[i]);
						map.put("carNo", carNo[i]);
						list.add(map);
					}
					SimpleAdapter adapter = new SimpleAdapter(WZSelect.this,
							list, R.layout.weizhangxiangiqng, new String[] {
									"time", "fine", "cityname", "id", "beha",
									"fen", "adress" }, new int[] {
									R.id.textView4, R.id.textView6,
									R.id.textView11, R.id.textView10, R.id.textView12, R.id.textView8,R.id.textView2});
					listView.setAdapter(adapter);
					
				} else {
					Toast.makeText(WZSelect.this, "查询失败", 0).show();
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
		setContentView(R.layout.activity_wzselect);
		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		listView=(ListView) findViewById(R.id.list_wzs);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new  Intent(WZSelect.this,Dingdanzhifu.class);
				intent.putExtra("recordsId", id[arg2]);
				intent.putExtra("carNo",carNo[arg2]);
				startActivity(intent);
			}
		});
		
	}

	

	public void chaxun(View v) {
		String chepai = editText1.getText().toString();
		String fadongji = editText2.getText().toString();
		WZSe.getRequest2(handler, chepai, fadongji);
	}

}
