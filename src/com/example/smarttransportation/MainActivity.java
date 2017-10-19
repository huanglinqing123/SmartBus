package com.example.smarttransportation;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

import com.Xundi.Wolf.Checichaxun;
import com.Xundi.Wolf.Chewei;
import com.Xundi.Wolf.Weather;
import com.Xundi.Wolf.Xianhao;
import com.Xundi.Wolf.Zhandian;
import com.example.tag.WZSelect;

@SuppressLint("NewApi") public class MainActivity extends Activity implements OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取点actionbar
        setContentView(R.layout.activity_main);//绑定布局
    }
    //限号查询按钮监听事件
    public void xianhaochaxun(View v){
    	Intent intent=new Intent(this,Xianhao.class);
		startActivity(intent);
    }
    //天气按钮查询监听事件
    public void weather(View v){
    	Intent intent = new Intent(this,Weather.class);
    	startActivity(intent);
    }
    //违章缴费查询监听事件
    public void weizhangjiaofei(View v){
    	Intent intent = new Intent(MainActivity.this,Weizhang2.class);
    	startActivity(intent);
    }
    //车位信息查询监听事件
    public void tingchechang(View v){
    	Intent intent = new Intent(MainActivity.this,Chewei.class);
    	startActivity(intent);
    }
    //自定义一个Popumenu//点击公交查询是 弹出来一个选择框则是popuMenu
    @TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") public void shishigongjiao(View v){
		PopupMenu menu=new PopupMenu(MainActivity.this, v);
		MenuInflater inflater=menu.getMenuInflater();
		inflater.inflate(R.menu.popument, menu.getMenu());//绑定自定义弹出框的布局
		menu.setOnMenuItemClickListener(this);//设置自定义弹出框的监听事件
		menu.show();//显示
	}
    

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	@Override
	//自定义弹出框的监听事件
	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.item1:
			//站点查询
			startActivity(new Intent(MainActivity.this,Zhandian.class));
			break;

		default:
			//车次查询
			startActivity(new Intent(MainActivity.this,Checichaxun.class));
			break;
		}
		return false;
	}
	//使用一个线程实现点击再按一次退出事件
	private static final int MSG_EXIT = 1;
	private static final int MSG_EXIT_WAIT = 2;
	private static final long EXIT_DELAY_TIME = 2000;
	private Handler mHandle = new Handler() {
	    public void handleMessage(Message msg) {
	        switch(msg.what) {
	            case MSG_EXIT:
	                if(mHandle.hasMessages(MSG_EXIT_WAIT)) {
	                    finish();
	                } else {    
	                    Toast.makeText(MainActivity.this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
	                    mHandle.sendEmptyMessageDelayed(MSG_EXIT_WAIT, EXIT_DELAY_TIME);
	                }
	                break;
	            case MSG_EXIT_WAIT:
	                break;
	        }
	    }
	};
	//获取手机返回按钮
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(KeyEvent.KEYCODE_BACK == keyCode) {
	        mHandle.sendEmptyMessage(MSG_EXIT);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
    
}
