package com.example.smarttransportation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class Welcome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		//执行首页跳转动画
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
			   startActivity(new Intent(Welcome.this,MainActivity.class));//跳转到首页
			   Welcome.this.finish();
				
			}
		}, 3000);//3000毫秒 设置3秒后跳转
	}

	

}

