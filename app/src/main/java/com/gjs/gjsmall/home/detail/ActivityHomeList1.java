package com.gjs.gjsmall.home.detail;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.gjs.gjsmall.R;
import com.gjs.gjsmall.home.baby.BabyActivity;
import com.gjs.gjsmall.home.cnc.CncActivity;
import com.gjs.gjsmall.home.map.MapActivity;
import com.gjs.http.GetHttp;
import com.javis.Adapter.Adapter_ListView_ware;
import com.javis.Adapter.Adapter_ListView_ware2;
import com.lesogo.cu.custom.xListview.XListView;
import com.lesogo.cu.custom.xListview.XListView.IXListViewListener;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;



public class ActivityHomeList1 extends Activity implements  OnTouchListener, IXListViewListener ,OnClickListener {
	private Bundle bundle;
	//显示所有商品的列表
	private XListView    listView;
	//整个顶部搜索控件，用于隐藏和显示底部整个控件
	private LinearLayout ll_search;
	//返回按钮
	private ImageView    iv_back;
	@SuppressWarnings("unused")
	private EditText     ed_search;
	private String       mSearch;
	//int     goods_to_id=0;
	private AnimationSet animationSet;
	/**第一次按下屏幕时的Y坐标*/
	float fist_down_Y = 0;
	/**请求数据的页数*/
	private int pageIndex = 0;
	/**存储网络返回的数据*/
	String url;
	String aaa;
	private TextView       RelativeLayout_ware_text;
	private RelativeLayout RelativeLayout_ware;
	/**存储网络返回的数据中的data字段*/
	private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();

	/**用来发起定位，添加取消监听*/
	public LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	/**维度*/
	private Double    latitude;
	/**经度*/
	private Double    longitude;
	private TextView detail_address_text;
	private View      fujin,meishi,zhineng,filter_1;
	private ImageView iv_1,iv_2,iv_3;
	private ImageView title_icon_location;
	int length;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_activity_home_list1);
		initView();
		dituInit();
		//请求网络数据

		mSearch ="家电";
		//请求网络数据
		new WareTask().execute();
		//onRefresh();
		ed_search.setText("");

		//判断是否有网络
		if (isNetworkAvailable(this)) {
			Toast.makeText(this.getApplicationContext(), "当前有可用网络！", Toast.LENGTH_LONG).show();

		}
		else {
			Toast.makeText(this.getApplicationContext(), "当前没有可用网络！", Toast.LENGTH_LONG).show();

		}

	}

	private void initView() {

		detail_address_text= (TextView) findViewById(R.id.detail_address_text);
		fujin=  findViewById(R.id.detail_activity_home_list1_fujin);
		meishi=  findViewById(R.id.detail_activity_home_list1_meishi);
		zhineng=  findViewById(R.id.detail_activity_home_list1_zhineng);
		filter_1=  findViewById(R.id.detail_activity_home_list1_filter1);
		iv_1= (ImageView) findViewById(R.id.detail_activity_home_list1_0_arrow_fujin);
		iv_2= (ImageView) findViewById(R.id.detail_activity_home_list1_0_arrow_meishi);
		iv_3= (ImageView) findViewById(R.id.detail_activity_home_list1_0_arrow_zhineng);
		title_icon_location= (ImageView) findViewById(R.id.title_icon_location);

		ll_search = (LinearLayout) findViewById(R.id.ll_choice);
		ed_search = (EditText) findViewById(R.id.ed_Searchware);
		iv_back = (ImageView) findViewById(R.id.aound_location_back);

		RelativeLayout_ware         = (RelativeLayout) findViewById(R.id.RelativeLayout_ware);
		RelativeLayout_ware_text    = (TextView) findViewById(R.id.RelativeLayout_ware_text);


		fujin.setOnClickListener(this);
		meishi.setOnClickListener(this);
		zhineng.setOnClickListener(this);
//		filter_1.setOnClickListener(this);
		iv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		title_icon_location.setOnClickListener(this);

		listView = (XListView) findViewById(R.id.listView_ware);
		listView.setOnTouchListener(this);
		listView.setXListViewListener(this);
		// 设置可以进行下拉加载的功能
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);
		listView.setVisibility(View.GONE);
		//        //先收索一次
		//        mSearch = ed_search.getText().toString().trim();
		//        //                mSearch=toUtf8(mSearch);
		//        //请求网络数据
		//        new WareTask().execute();
		//       // onRefresh();
		//        ed_search.setText("");
		onLoad();

		ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				mSearch = ed_search.getText().toString().trim();
				//                mSearch=toUtf8(mSearch);
				//请求网络数据
				//new WareTask().execute();
				onRefresh();
				ed_search.setText("");

				return false;
			}
		});

		RelativeLayout_ware_text.setText("赶紧搜索你想要的商品吧...");
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		float y = event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				//第一次按下时的坐标
				fist_down_Y = y;
				break;
			case MotionEvent.ACTION_MOVE:
				// 向上滑动，隐藏搜索栏
				if (fist_down_Y - y > 250 && ll_search.isShown()) {
					if (animationSet != null) {
						animationSet = null;
					}
					animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.up_out);
					ll_search.startAnimation(animationSet);
					ll_search.setY(-100);
					ll_search.setVisibility(View.GONE);
				}
				// 向下滑动，显示搜索栏
				if (y - fist_down_Y > 250 && !ll_search.isShown()) {
					if (animationSet != null) {
						animationSet = null;
					}
					animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.down_in);
					ll_search.startAnimation(animationSet);
					ll_search.setY(0);
					ll_search.setVisibility(View.VISIBLE);
				}
				break;

		}
		return false;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.detail_activity_home_list1_fujin:

//				if(filter_1.getVisibility()==View.GONE){
//					filter_1.setVisibility(View.VISIBLE);//对应的布局设置显示
//					iv_1.setVisibility(View.VISIBLE);//对应上面的小箭头显示
//				}else{               //设置gone不显示
//					filter_1.setVisibility(View.GONE);
//					setAllImageArrowGone();
//				}
//				filter_1.setVisibility(View.VISIBLE);//对应的布局设置显示

				break;
			case R.id.detail_activity_home_list1_meishi:
//				if(filter_1.getVisibility()==View.GONE){
//					filter_1.setVisibility(View.VISIBLE);//对应的布局设置显示
//					iv_2.setVisibility(View.VISIBLE);//对应上面的小箭头显示
//				}else{               //设置gone不显示
//					filter_1.setVisibility(View.GONE);
//					setAllImageArrowGone();
//				}
				break;
			case R.id.detail_activity_home_list1_zhineng:
//				if(filter_1.getVisibility()==View.GONE){
//					filter_1.setVisibility(View.VISIBLE);//对应的布局设置显示
//					iv_3.setVisibility(View.VISIBLE);//对应上面的小箭头显示
//				}else{               //设置gone不显示
//					filter_1.setVisibility(View.GONE);
//					setAllImageArrowGone();
//				}
				break;
			case R.id.title_icon_location:
				//跳转到定位
				Intent intent =new Intent(ActivityHomeList1.this, MapActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
	}
	private void setAllImageArrowGone(){
		iv_1.setVisibility(View.GONE);
		iv_2.setVisibility(View.GONE);
		iv_3.setVisibility(View.GONE);
	}


	private class WareTask
			extends AsyncTask<Void, Void, HashMap<String, Object>>
	{

		ProgressDialog dialog = null;

		@Override
		protected void onPreExecute() {
			if (dialog == null) {
				dialog = ProgressDialog.show(ActivityHomeList1.this, "", "正在加载...");
				dialog.show();
			}


		}

		@Override
		protected HashMap<String, Object> doInBackground(Void... arg0) {
			HashMap<String, Object>            hashMap1;
			ArrayList<HashMap<String, Object>> mArray1  = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object>            hashMap2 = new HashMap<String, Object>();
			for (int i = 1; i < 5; i++) {
				if(i==2){
					i++;
				}
				String[] goods_image_str={
						"https://img.alicdn.com/imgextra/i1/2587633828/TB2c6WMnFXXXXaUXXXXXXXXXXXX_!!2587633828.jpg_430x430q90.jpg",
						"https://img.alicdn.com/imgextra/i1/2587633828/TB2c6WMnFXXXXaUXXXXXXXXXXXX_!!2587633828.jpg_430x430q90.jpg",
						"https://img.alicdn.com/imgextra/i1/2587633828/TB2c6WMnFXXXXaUXXXXXXXXXXXX_!!2587633828.jpg_430x430q90.jpg",
						"https://img.alicdn.com/imgextra/i1/2587633828/TB2c6WMnFXXXXaUXXXXXXXXXXXX_!!2587633828.jpg_430x430q90.jpg",
						"https://img.alicdn.com/imgextra/i1/2587633828/TB2c6WMnFXXXXaUXXXXXXXXXXXX_!!2587633828.jpg_430x430q90.jpg"};
				String t = i+"";

				//			try {
				//				t= URLEncoder.encode(mSearch+"", "UTF-8");//字符编码和文档编码一致
				//			} catch (UnsupportedEncodingException e) {
				//				e.printStackTrace();
				//			}
				url = "http://gjs.wsh68.com/mobile/index.php?act=store&op=store_detail&store_id=" + t;

				//请求数据，返回json
				String json = GetHttp.RequstGetHttp(url);
				try {

					JSONTokener jsonParser = new JSONTokener(json);

					JSONObject person = (JSONObject) jsonParser.nextValue();


					JSONObject datas = person.getJSONObject("datas");


					JSONObject store_info = datas.getJSONObject("store_info");



						String store_company_name           = store_info.getString("store_company_name");//名字
						String area_info          = store_info.getString("area_info");//省市
						String store_address    = store_info.getString("store_address");//地址
						String goods_image_url      = goods_image_str[i];//图片
						String store_id             = store_info.getString("store_id");//id


						hashMap1 = new HashMap<String, Object>();
						hashMap1.put("store_company_name", store_company_name);
						hashMap1.put("area_info", area_info);
						hashMap1.put("store_address", store_address);
						hashMap1.put("goods_image_url", goods_image_url);
						hashMap1.put("store_id", store_id);

						mArray1.add(hashMap1);


				} catch (Exception e) {
					// 异常处理代码
				}
			}
			hashMap2.put("data", mArray1);
			//goods_to_id=Integer.getInteger(goods_id);
			// aaa=hashMap2+ "ddddds";
			//            hashMap = new HashMap<String, Object>();
			//            hashMap.put("data", mArray);
			//aaa="aaaa"+hashMap+ "ddddd";
			return hashMap2;
		}
		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(HashMap<String, Object> result) {

			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
				dialog = null;
			}
			//            Toast.makeText(WareActivity.this, "size"+aaa, Toast.LENGTH_LONG).show();//测试用
			//            ed_search.setText("size"+aaa);//测试用
			//如果网络数据请求失败，那么显示默认的数据
			if (result != null && result.get("data") != null) {
				//得到data字段的数据
				arrayList.addAll((Collection<? extends HashMap<String, Object>>) result.get("data"));
				if (arrayList.size()!=0){
					listView.setVisibility(View.VISIBLE);
					RelativeLayout_ware.setVisibility(View.GONE);
				}else {
					listView.setVisibility(View.GONE);
					RelativeLayout_ware.setVisibility(View.VISIBLE);
					RelativeLayout_ware_text.setText("不好意思，没找到商品...请重新输入");
				}
				//                Toast.makeText(WareActivity.this, "size"+result, Toast.LENGTH_LONG).show();

				listView.setAdapter(new Adapter_ListView_ware2(ActivityHomeList1.this, arrayList));
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

						Intent intent8=new Intent(ActivityHomeList1.this, CncActivity.class);
						//用Bundle携带数据
						bundle=new Bundle();
						//传递name参数为tinyphp
						bundle.putString("url", "http://gjs.wsh68.com/wap/tmpl/go_store.html?store_id="+arrayList.get(position-1).get("store_id").toString().trim());
						bundle.putString("name", "店铺");
						intent8.putExtras(bundle);
						startActivity(intent8);

					}
				});

			} else {

				listView.setAdapter(new Adapter_ListView_ware(ActivityHomeList1.this));
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
					{
						Intent intent2 = new Intent(ActivityHomeList1.this, BabyActivity.class);
						//用Bundle携带数据
						bundle=new Bundle();
						//传递name参数为tinyphp
						bundle.putInt("num", 0);
						intent2.putExtras(bundle);
						startActivity(intent2);
					}
				});
			}

			// 停止刷新和加载
			onLoad();

		}

	}

	/** 刷新 */
	@Override
	public void onRefresh() {
		// 刷新数据
		//        pageIndex = 0;
		arrayList.clear();
		//        //new WareTask().execute();
		//        // 停止刷新和加载

		new WareTask().execute();
		onLoad();
	}

	/** 加载更多 */
	@Override
	public void onLoadMore() {
		pageIndex += 1;
		if (pageIndex >= (length/8) ){
			Toast.makeText(this, "已经最后一页了", Toast.LENGTH_SHORT)
				 .show();
			onLoad();
			return;
		}
		new WareTask().execute();

	}

	/** 停止加载和刷新 */
	private void onLoad() {
		listView.stopRefresh();
		// 停止加载更多
		listView.stopLoadMore();

		// 设置最后一次刷新时间
		listView.setRefreshTime(getCurrentTime(System.currentTimeMillis()));
	}

	/** 简单的时间格式 */
	public static SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

	public static String getCurrentTime(long time) {
		if (0 == time) {
			return "";
		}

		return mDateFormat.format(new Date(time));

	}

	private void dituInit() {
		mLocClient = new LocationClient(getApplicationContext());
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(5000);
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		//option.setIsNeedAddress(true);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}


	/**
	 * 继承BDLocationListener定位接口类，获取定位结果
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			detail_address_text.setText(location.getAddrStr());
		}
		@Override
		public void onReceivePoi(BDLocation poiLocation) {
		}
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
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
				Context.CONNECTIVITY_SERVICE);

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

}
