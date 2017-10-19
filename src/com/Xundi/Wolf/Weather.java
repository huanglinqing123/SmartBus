package com.Xundi.Wolf;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.smarttransportation.R;

public class Weather extends Activity{


		private WebView view;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_weather);
			view=(WebView) findViewById(R.id.webView1);
			//将天气预报h5页面放置服务器，通过webview加载一个网页
			view.loadUrl("http://www.huanglinqing.com/WildWlofWeather/");
		  
		}
	}


