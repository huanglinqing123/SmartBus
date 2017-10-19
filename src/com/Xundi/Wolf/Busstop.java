package com.Xundi.Wolf;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Tingchechang.Tingchechang;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarttransportation.R;

public class Busstop extends Activity {

	private TextView textView1, textView2, textView3, textView4, textView5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busstop);
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView3 = (TextView) findViewById(R.id.textView3);
		textView4 = (TextView) findViewById(R.id.textView4);
		textView5 = (TextView) findViewById(R.id.textView5);
		Intent intent = getIntent();
		String ccid = intent.getStringExtra("ccid");
		String ccmc = intent.getStringExtra("ccmc");
		String ccdz = intent.getStringExtra("ccdz");
		textView1.setText(ccmc);
		textView2.setText(ccdz);
		Tingchechang.getRequest1(handler, ccid);

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String jsondate = (String) msg.obj;
			StringBuffer buffer = new StringBuffer();
			try {
				JSONObject jsonObject = new JSONObject(jsondate);
				if (jsonObject.getInt("error_code") == 0) {
					JSONArray array = new JSONArray(
							jsonObject.getString("result"));
					for (int i = 0; i < array.length(); i++) {
						JSONObject jsonObject2 = array.getJSONObject(i);
						String CCFL = jsonObject2.getString("CCFL");
						String CCLX = jsonObject2.getString("CCLX");
						buffer.append(CCFL + "\n" + CCLX);
						textView3.setText(buffer);
						 String BTTCJG = jsonObject2.getString("BTTCJG");
						// Toast.makeText(Busstop.this, BTTCJG, 0).show();
						 String WSTCJG = jsonObject2.getString("WSTCJG");
						 textView4.setText(BTTCJG);
						 textView5.setText(WSTCJG);
					}

				} else {
					Toast.makeText(Busstop.this, "查询失败", 0).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};
};