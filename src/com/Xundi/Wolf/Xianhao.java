package com.Xundi.Wolf;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Weihao.Weihao;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarttransportation.R;

public class Xianhao extends Activity {
	//定义一个自动提示输入框
	private AutoCompleteTextView autoCompleteTextView;
	private ArrayAdapter adapter;
	private TextView textView,textView2;
	//定义一个数组保存尾号限行支持的城市，若输入的城市不在该数组中，则查询失败
	String chengshi[] = { "北京", "贵阳", "杭州", "兰州", "天津", "成都", "南昌", "长春" };
	List<String> list=Arrays.asList(chengshi);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xianhao);//绑定布局
		textView = (TextView) findViewById(R.id.textView1);
		textView2=(TextView) findViewById(R.id.textView2);
		autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		//将城市列表绑定到listview中
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, chengshi);
		//将城市列表绑定到自动提示输入框
		autoCompleteTextView.setAdapter(adapter);
	}
   
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			//获取json结果
			String jsondata = (String) msg.obj;
			StringBuffer buffer = new StringBuffer();
			StringBuffer buffer2=new StringBuffer();
			try {
				JSONObject jsonObject = new JSONObject(jsondata);
				//判断是否查询成功
				if (jsonObject.getInt("error_code") == 0) {
					Toast.makeText(Xianhao.this, "查询成功", 0).show();
					String result = jsonObject.getString("result");
					JSONObject jsonObject2 = new JSONObject(result);
					String data=jsonObject2.getString("date");
					buffer.append(data+"\n\n");
					final String fine=jsonObject2.getString("fine");
					String remarks=jsonObject2.getString("remarks");
					
					JSONArray array = jsonObject2.getJSONArray("des");
					String xxweihao=jsonObject2.getString("xxweihao");
                    textView2.setText("限行尾号："+xxweihao);			
				
					for (int i = 0; i < array.length(); i++) {
						JSONObject jsonObject3 = array.getJSONObject(i);
						String time = jsonObject3.getString("time");
						String place = jsonObject3.getString("place");
						buffer.append(time + "\n" + place + "\n\n");
					}
					buffer.append(fine+"\n");
					buffer.append(remarks+"\n");
					
				} else {
					Toast.makeText(Xianhao.this, "查询失败", 0).show();
				}
				textView.setText(buffer);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.xianhao, menu);
	// return true;
	// }
	//点击查询按钮查询信息
	public void huang(View v) {
		textView.setText("");
		textView2.setText("");
		String city = autoCompleteTextView.getText().toString();
		if (list.contains(city)) {
			//api接口传入的参数是拼音所以将汉字转化为拼音
			if (city.equals("北京")) {
				city = "beijing";
			} else if (city.equals("贵阳")) {
				city = "guiyang";
			} else if (city.equals("杭州")) {
				city = "hangzhou";
			} else if (city.equals("兰州")) {
				city = "lanzhou";
			} else if (city.equals("天津")) {
				city = "tianjin";
			} else if (city.equals("成都")) {
				city = "chengdu";
			} else if (city.equals("南昌")) {
				city = "nanchang";
			} else if (city.equals("长春")) {
				city = "changchun";
			}
			
			Weihao.getRequest2(handler, city);
			
		}else{
			Toast.makeText(Xianhao.this, "暂不支持查询该城市", 0).show();
		}
		
		}
		

}
