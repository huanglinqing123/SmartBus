package com.example.smarttransportation;

import com.example.smarttransportation.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Weizhang2 extends Activity {

	private WebView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weizhang2);
        view=(WebView) findViewById(R.id.webView1);  
    	view.getSettings().setJavaScriptEnabled(true);// 启用js
		view.getSettings().setBlockNetworkImage(false);// 解决图片不显示
		/* 设置支持Js,必须设置的,不然网页基本上不能看 */
		view.getSettings().setJavaScriptEnabled(true);
		/* 设置缓存模式,我这里使用的默认,不做多讲解 */
		view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		/* 设置为true表示支持使用js打开新的窗口 */
		view.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		/* 大部分网页需要自己保存一些数据,这个时候就的设置下面这个属性 */
		view.getSettings().setDomStorageEnabled(true);
		/* 设置为使用webview推荐的窗口 */
		view.getSettings().setUseWideViewPort(true);
		/* 设置网页自适应屏幕大小 ---这个属性应该是跟上面一个属性一起用 */
		view.getSettings().setLoadWithOverviewMode(true);
		/* HTML5的地理位置服务,设置为true,启用地理定位 */
		view.getSettings().setGeolocationEnabled(true);
		/* 设置是否允许webview使用缩放的功能,我这里设为false,不允许 */
		view.getSettings().setBuiltInZoomControls(false);
		/* 提高网页渲染的优先级 */
		view.getSettings().setRenderPriority(RenderPriority.HIGH);
		/* 设置显示水平滚动条,就是网页右边的滚动条.我这里设置的不显示 */
		view.setHorizontalScrollBarEnabled(false);
		/* 指定垂直滚动条是否有叠加样式 */
		view.setVerticalScrollbarOverlay(true);
		/* 设置滚动条的样式 */
		view.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		/* 这个不用说了,重写WebChromeClient监听网页加载的进度,从而实现进度条 */
		view.setWebChromeClient(new WebChromeClient());
		/* 同上,重写WebViewClient可以监听网页的跳转和资源加载等等... */
		view.setWebViewClient(new WebViewClient());
        view.loadUrl("http://www.huanglinqing.com/weizhang/");
        
    }

   
    
}
