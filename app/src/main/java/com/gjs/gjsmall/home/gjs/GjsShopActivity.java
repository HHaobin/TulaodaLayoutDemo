package com.gjs.gjsmall.home.gjs;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjs.gjsmall.R;
import com.gjs.gjsmall.home.cnc.LJWebView;

import java.util.HashMap;

public class GjsShopActivity
        extends Activity implements View.OnClickListener{
    //private TextView textview;
    private ImageView iv_cnc_opinion_back;
    private TextView explain_cnc_top_title;
    private WebView webview;  
    private String path="http://gjs.wsh68.com/shop/index.php?act=show_store&op=index&store_id=1";
    private LJWebView mLJWebView = null;
    HashMap<String, String> hashMap=new HashMap<String, String>();

    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_cnc);
        Bundle bundle = this.getIntent().getExtras();
        //接收num和name值
        String url  = bundle.getString("url");
        String name = bundle.getString("name");
        path=url;
        //webview = (WebView) findViewById(R.id.webview);
        iv_cnc_opinion_back = (ImageView) findViewById(R.id.iv_cnc_opinion_back);
        explain_cnc_top_title = (TextView) findViewById(R.id.explain_cnc_top_title);
//        textview = (TextView) findViewById(R.id.textview);
        explain_cnc_top_title.setText(name);
        iv_cnc_opinion_back.setOnClickListener(this);
        hashMap.put("User-Agent","Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; WOW64; Trident/6.0)");

        init2();
      //  init();
        //Toast.makeText(this,"..............................................."+hashMap,Toast.LENGTH_LONG).show();
    }

    private void init2() {
        mLJWebView = (LJWebView) findViewById(R.id.web);
        mLJWebView.setProgressStyle(LJWebView.Circle);
        mLJWebView.setBarHeight(8);
        mLJWebView.setClickable(true);
        mLJWebView.setUseWideViewPort(true);
        mLJWebView.setSupportZoom(true);
        mLJWebView.setBuiltInZoomControls(true);
        mLJWebView.setJavaScriptEnabled(true);
        mLJWebView.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mLJWebView.setWebViewClient(new WebViewClient() {
            //重写此方法，浏览器内部跳转
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("跳的URL =" + url);
                view.loadUrl(url,hashMap);
                return true;
            }
        });

        mLJWebView.loadUrl(path,hashMap);//添加请求头 ，阻止加载wap，进入pc端

    }


    private void init(){
        //启用支持javascript
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
//User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0
        //    	//设置 缓存模式
        //    	webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webview.getSettings().setDomStorageEnabled(true);
        //优先使用缓存
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        //判断是否有网络
        if (isNetworkAvailable(GjsShopActivity.this))
        {
            Toast.makeText(getApplicationContext(), "当前有可用网络！", Toast.LENGTH_LONG).show();
        }
        else
        {

            Toast.makeText(getApplicationContext(), "当前没有可用网络！", Toast.LENGTH_LONG).show();
        }


        //    	String cacheDirPath = getCacheDir().getAbsolutePath()+ "/webViewCache ";
        //    	WebSettings webSettings = webview.getSettings();
        //    	//开启 database storage API 功能
        //    	webSettings.setDatabaseEnabled(true);
        //    	//设置数据库缓存路径
        //    	webSettings.setDatabasePath(cacheDirPath);
        //    	//开启Application H5 Caches 功能
        //    	webSettings.setAppCacheEnabled(true);
        //    	//设置Application Caches 缓存目录
        //    	webSettings.setAppCachePath(cacheDirPath);


        //    	//不使用缓存：
        //    	webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        //WebView加载web资源
        webview.loadUrl(path,hashMap);



        //textview.setText(webview.getUrl().toString());
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url,hashMap);
                return true;
            }
        });
        //页面加载过程
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成

                } else {
                    // 加载中
                    //textview.setText(webview.getUrl().toString());
                }

            }
        });

        //支持javascript
        webview.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webview.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webview.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webview.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setLoadWithOverviewMode(true);

    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(mLJWebView.getmWebView().canGoBack())
            {
                mLJWebView.getmWebView().goBack();//返回上一页面
                return true;
            }
            else
            {
                finish();
               // System.exit(0);//退出程序
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 检查当前网络是否可用
     *
     * @param
     * @return
     */

    public boolean isNetworkAvailable(Activity activity)
    {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_cnc_opinion_back:
                finish();
                break;



            default:
                break;
        }
    }
}