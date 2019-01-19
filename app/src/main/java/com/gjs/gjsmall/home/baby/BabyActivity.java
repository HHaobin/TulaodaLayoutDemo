package com.gjs.gjsmall.home.baby;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gjs.MyView.BabyPopWindow;
import com.gjs.MyView.BabyPopWindow.OnItemClickListener;
import com.gjs.gjsmall.Data.Data;
import com.gjs.gjsmall.R;
import com.gjs.gjsmall.home.BuynowActivity;
import com.gjs.gjsmall.home.cnc.CncActivity;
import com.gjs.gjsmall.utils.StreamTool;
import com.javis.Adapter.Adapter_ListView_detail;
import com.lesogo.cu.custom.ScaleView.HackyViewPager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 单个商品详情界面
 * @author
 *
 */
public class BabyActivity extends FragmentActivity
		implements OnItemClickListener, OnClickListener
{

	NfcAdapter nfcAdapter;
	private HackyViewPager  viewPager;
	private ArrayList<View> allListView;
	private int[] resId = { R.drawable.detail_show_1, R.drawable.detail_show_2, R.drawable.detail_show_3, R.drawable.detail_show_4, R.drawable.detail_show_5, R.drawable.detail_show_6 };
	private ListView      listView;
	private ImageView     iv_baby_collection;
	/**弹出商品订单信息详情*/
	private BabyPopWindow popWindow;
	/** 用于设置背景暗淡 */
	private LinearLayout all_choice_layout = null;
	/**判断是否点击的立即购买按钮*/
	boolean isClickBuy = false;
	/**是否添加收藏*/
	private static boolean isCollection=false;
	/**ViewPager当前显示页的下标*/
	private int position=0;

	protected static final int SUCCESS = 0;
	protected static final int ERROR = 1;

	private String json;
	private String url;
	private int id=0;
	String [] temp = null;
	private TextView goods_name_1,goods_price_1,goods_item_1,goods_item_2,goods_item_3;
	private  ImageView iv_refresh;
	String goods_name=null;
	String goods_price=null;
	String goods_marketprice=null;
	String goods_salenum=null;
	String store_id="1";
	String store_name=null;
	String store_avatar=null;

	RelativeLayout baby_progress_circle;
	private RelativeLayout  iv_baby_collection2;
	String[] image={"http://gjs.wsh68.com/data/upload/shop/common/default_goods_image_360.gif",
					"http://gjs.wsh68.com/data/upload/shop/common/default_goods_image_360.gif",
					"http://gjs.wsh68.com/data/upload/shop/common/default_goods_image_360.gif",
					"http://gjs.wsh68.com/data/upload/shop/common/default_goods_image_360.gif"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.babydetail_a);

		iv_baby_collection2=(RelativeLayout)findViewById(R.id.iv_baby_collection2);
		goods_name_1= (TextView) findViewById(R.id.goods_name_1);
		goods_price_1= (TextView) findViewById(R.id.goods_price_1);
		goods_item_1= (TextView) findViewById(R.id.goods_item_1);
		goods_item_2= (TextView) findViewById(R.id.goods_item_2);
		goods_item_3= (TextView) findViewById(R.id.goods_item_3);
		iv_refresh= (ImageView) findViewById(R.id.iv_refresh);
		baby_progress_circle= (RelativeLayout) findViewById(R.id.baby_progress_circle);

		iv_baby_collection2.setOnClickListener(this);

		baby_progress_circle.setVisibility(View.VISIBLE);
		Bundle bundle = this.getIntent().getExtras();
		//接收num和name值
		id  = bundle.getInt("num");
		Data.arrayList_cart_id=id;
		//得到保存的收藏信息
		getSaveCollection();
		initView();
		popWindow = new BabyPopWindow(this);
		popWindow.setOnItemClickListener(this);
		login();
		//Toast.makeText(BabyActivity.this,",,,,,,"+ goods_name, Toast.LENGTH_SHORT).show();
		//abby();

	}

	@SuppressLint("NewApi")
	private void initView() {

		iv_refresh.setOnClickListener(this);

		// 获取默认的NFC控制器
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (nfcAdapter == null) {
			//Toast.makeText(this, "该设备不支持NFC"+num, Toast.LENGTH_SHORT).show();
		}

		((ImageView) findViewById(R.id.iv_back)).setOnClickListener(this);
		((TextView) findViewById(R.id.put_in)).setOnClickListener(this);
		((TextView) findViewById(R.id.buy_now)).setOnClickListener(this);
		iv_baby_collection=(ImageView) findViewById(R.id.iv_baby_collection);
		iv_baby_collection.setOnClickListener(this);
		all_choice_layout = (LinearLayout) findViewById(R.id.all_choice_layout);
		listView = (ListView) findViewById(R.id.listView_Detail);
		listView.setFocusable(false);
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		listView.setAdapter(new Adapter_ListView_detail(this));
		listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				//进入浏览器浏览网页
//				Uri    uri    = Uri.parse("http://yecaoly.taobao.com");
//				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//				startActivity(intent);
			}
		});
		initViewPager(image);

		if (isCollection) {
			//如果已经收藏，则显示收藏后的效果
			iv_baby_collection.setImageResource(R.drawable.second_2_collection);
		}
	}

	@Override
	public void onClick(View view) {
		Bundle bundle;
		switch (view.getId()) {
			case R.id.iv_back:
				//返回
				finish();
				break;
			case R.id.iv_refresh:
				//刷新
				login();
				break;
			case R.id.iv_baby_collection:
				//收藏
				if (isCollection) {
					//提示是否取消收藏
					cancelCollection();
				}else {
					isCollection=true;
					setSaveCollection();
					//如果已经收藏，则显示收藏后的效果
					iv_baby_collection.setImageResource(R.drawable.second_2_collection);
					Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.put_in:
				//添加购物车
				isClickBuy = false;
				//setBackgroundBlack(all_choice_layout, 0);
				popWindow.showAsDropDown(view);
				break;
			case R.id.buy_now:
				//立即购买
				isClickBuy = true;
				//setBackgroundBlack(all_choice_layout, 0);
				popWindow.showAsDropDown(view);
				break;
			case R.id.iv_baby_collection2:
				Intent intent8=new Intent(this, CncActivity.class);
				//用Bundle携带数据
				bundle=new Bundle();
				//传递name参数为tinyphp
				bundle.putString("url", "http://gjs.wsh68.com/wap/tmpl/go_store.html?store_id="+store_id);
				bundle.putString("name", "店铺");
				intent8.putExtras(bundle);
				startActivity(intent8);
				break;
			default:
				break;
		}
	}



	private void initViewPager(String[] image) {

		if (allListView != null) {
			allListView.clear();
			allListView = null;
		}
		allListView = new ArrayList<View>();

		for (int i = 0; i < image.length; i++) {
			View      view      = LayoutInflater.from(this).inflate(R.layout.pic_item, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);


			try {
//				URL               url               = new URL("http://gjs.wsh68.com/data/upload/shop/store/goods/1/1_04418254218437108_360.jpg"); //path图片的网络地址
//				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//				if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//					Toast.makeText(BabyActivity.this,">...."+image[0]+ goods_name, Toast.LENGTH_SHORT).show();
//					Bitmap bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
//					imageView.setImageBitmap(bitmap);//加载到ImageView上
//				}
				DownloadImageTask localDownloadImageTask = new DownloadImageTask(imageView);
				String[] arrayOfString = new String[1];
				arrayOfString[0] = image[i];
				localDownloadImageTask.execute(arrayOfString);

			} catch (Exception e) {
				//imageView.setImageResource(resId[i]);

			}
			//imageView.setImageResource(resId[i]);
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
//					//挑战到查看大图界面
//					Intent intent = new Intent(BabyActivity.this, ShowBigPictrue.class);
//					intent.putExtra("position", position);
//					startActivity(intent);
				}
			});
			allListView.add(view);
		}

		viewPager = (HackyViewPager) findViewById(R.id.iv_baby);
		ViewPagerAdapter adapter = new ViewPagerAdapter();
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				position=arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		viewPager.setAdapter(adapter);

	}

	private class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return allListView.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (View) arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = allListView.get(position);
			container.addView(view);
			return view;
		}

	}

	//点击弹窗的确认按钮的响应
	@Override
	public void onClickOKPop() {
		setBackgroundBlack(all_choice_layout, 1);

		if (isClickBuy) {
			//如果之前是点击的立即购买，那么就跳转到立即购物界面
			Intent intent = new Intent(BabyActivity.this, BuynowActivity.class);
			startActivity(intent);
		}else {
			//Toast.makeText(this, "添加到购物车成功", Toast.LENGTH_SHORT).show();
		}
	}

	/** 控制背景变暗 0变暗 1变亮 */
	public void setBackgroundBlack(View view, int what) {
		switch (what) {
			case 0:
				view.setVisibility(View.VISIBLE);
				break;
			case 1:
				view.setVisibility(View.GONE);
				break;
		}
	}

	/**保存是否添加收藏*/
	private void setSaveCollection(){
		SharedPreferences sp     =getSharedPreferences("SAVECOLLECTION", Context.MODE_PRIVATE);
		Editor            editor =sp.edit();
		editor.putBoolean("isCollection", isCollection);
		editor.commit();
	}
	/**得到保存的是否添加收藏标记*/
	private void getSaveCollection(){
		SharedPreferences sp =getSharedPreferences("SAVECOLLECTION", Context.MODE_PRIVATE);
		isCollection=sp.getBoolean("isCollection", false);

	}
	/**取消收藏*/
	private  void cancelCollection(){
		AlertDialog.Builder dialog =new AlertDialog.Builder(this);
		dialog.setTitle("是否取消收藏");
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				isCollection=false;
				//如果取消收藏，则显示取消收藏后的效果
				iv_baby_collection.setImageResource(R.drawable.second_2);
				setSaveCollection();
			}
		});
		dialog.setNegativeButton("取消", null);
		dialog.create().show();

	}

	private class DownloadImageTask extends AsyncTask<String, Void, Drawable>
	{
		ImageView imageView;
		private DownloadImageTask(ImageView imageView)
		{

			this.imageView = imageView;
		}

		protected Drawable doInBackground(String[] paramArrayOfString)
		{
			return loadImageFromNetwork(paramArrayOfString[0]);
		}

		protected void onPostExecute(Drawable paramDrawable)
		{
			imageView.setImageDrawable(paramDrawable);
		}
	}

	private Drawable loadImageFromNetwork(String paramString)
	{
		Drawable localDrawable1;
		try
		{
			Drawable localDrawable2 = Drawable.createFromStream(new URL(paramString).openStream(), "image.jpg");
			localDrawable1 = localDrawable2;
			if (localDrawable1 == null)
			{
				Log.d("test", "null drawable");
				return localDrawable1;
			}
		}
		catch (IOException localIOException)
		{
			while (true)
			{
				Log.d("test", localIOException.getMessage());
				localDrawable1 = null;
			}
		}
		return localDrawable1;
	}




	String path;
	//商品详情的 请求方法
	public void login(){
		//发请求
		new Thread(){
			public void run() {

				//  http://188.188.4.100:8080/day06_android/login?number=110&password=123
				try {

					//					number =URLEncoder.encode(number, "UTF-8");
					//					path = path+"?number="+number+"&password="+password;
					path="http://gjs.wsh68.com/mobile/index.php?act=goods&op=goods_detail&goods_id=" + id;
					//相当于打开了一个 浏览器客户端
					HttpClient client = new DefaultHttpClient();

					//get方式的请求
					HttpGet get = new HttpGet(path);

					//获得的响应 对象
					HttpResponse response = client.execute(get);

					//获得状态行对象,然后再 获得状态行中的状态码
					int code = response.getStatusLine().getStatusCode();

					if(code==200){

						//获得响应体,  获得响应体中的 流的数据
						//接下来的代码跟之前一样的
						InputStream in = response.getEntity().getContent();

						String value = StreamTool.decodeStream(in);
						//						goods_name_1.setText(value);
						//						Toast.makeText(BabyActivity.this, value, Toast.LENGTH_SHORT).show();
						//						//发消息
						//						Message msg = Message.obtain();
						//						msg.what = SUCCESS;
						//						msg.obj = value;
						//						handler.sendMessage(msg);


						ArrayList<String> mArray1;
						ArrayList<String> mArray2;
						HashMap<String, Object> hashMap=new HashMap<String, Object>();
						HashMap<String, Object> hashMap1=null;
						HashMap<String, Object> hashMap2;
						HashMap<String, Object> hashMap3;
						HashMap<String, Object> hashMap4;

						try {

							JSONTokener jsonParser = new JSONTokener(value);

							JSONObject person = (JSONObject) jsonParser.nextValue();

							JSONObject datas=person.getJSONObject("datas");
							//goods_name_1.setText("....."+datas);
							JSONObject goods_info = datas.getJSONObject("goods_info");
							String goods_image = datas.getString("goods_image");
							JSONObject store_info = datas.getJSONObject("store_info");

							 goods_name=goods_info.getString("goods_name");//名字
							 goods_price=goods_info.getString("goods_price");//价格
							 goods_marketprice=goods_info.getString("goods_marketprice");//市场价
							 goods_salenum=goods_info.getString("goods_salenum");//销量

							store_id=store_info.getString("store_id");//id
							store_name=store_info.getString("store_name");//名字
							store_avatar=store_info.getString("avatar");//图片


							hashMap3= new HashMap<String, Object>();//goods_info
							hashMap3.put("goods_name", goods_name);
							hashMap3.put("goods_price", goods_price);
							hashMap3.put("goods_marketprice", goods_marketprice);
							hashMap3.put("goods_salenum", goods_salenum);

							hashMap4= new HashMap<String, Object>();//store_info
							hashMap4.put("store_id", store_id);
							hashMap4.put("store_name", store_name);
							hashMap4.put("avatar", store_avatar);

							//										mArray1 = new ArrayList<String>();
							//										for (int i = 0; i < goods_image.length(); i++) {
							//											String index1 = goods_image.getString(i);
							//											mArray1.add(index1);//图片
							//										}

							hashMap2= new HashMap<String, Object>();//datas
							hashMap2.put("goods_info", hashMap3);
							hashMap2.put("goods_image", goods_image);
							hashMap2.put("store_info", hashMap4);
							//hashMap2.put("goods_image", mArray1);

							hashMap1= new HashMap<String, Object>();//person总
							hashMap1.put("datas", hashMap2);
							//goods_name_1.setText("....."+hashMap1);
//							{
//								goods_name_1.setText(goods_name);goods_name_1.setText("ccccc");
//								goods_price_1.setText(goods_price);
//								goods_item_2.setText(goods_salenum);
//
//							}


							//通知 ui 更新
							Message msg = Message.obtain();
							msg.what= SUCCESS;
							msg.obj =goods_name+"/，/"+goods_price+"/，/"+goods_marketprice+"/，/"+goods_salenum+"/，/"+goods_image;

							handler.sendMessage(msg);

							Data.baby_goods_id=""+id;
							Data.baby_goods_price=goods_price;
							Data.baby_goods_ku="2000";
							String[] temp=null;
							temp=goods_image.split(",");
							Data.baby_goods_pic=temp[0];

						}catch (Exception e) {
							// 异常处理代码

						}
					}

				} catch (Exception e) {
					//					e.printStackTrace();
					//					Message msg = Message.obtain();
					//					msg.what = ERROR;
					//					handler.sendMessage(msg);
				}


			};

		}.start();
		//goods_name="1111111";
	}

	//添加到购物车的 请求方法
	public void toCart(){
		//发请求
		new Thread(){
			public void run() {

				//  http://188.188.4.100:8080/day06_android/login?number=110&password=123
				try {

					//					number =URLEncoder.encode(number, "UTF-8");
					//					path = path+"?number="+number+"&password="+password;
					path="http://gjs.wsh68.com/mobile/index.php?act=goods&op=goods_detail&goods_id=" + id;
					//相当于打开了一个 浏览器客户端
					HttpClient client = new DefaultHttpClient();

					//get方式的请求
					HttpGet get = new HttpGet(path);

					//获得的响应 对象
					HttpResponse response = client.execute(get);

					//获得状态行对象,然后再 获得状态行中的状态码
					int code = response.getStatusLine().getStatusCode();

					if(code==200){

						//获得响应体,  获得响应体中的 流的数据
						//接下来的代码跟之前一样的
						InputStream in = response.getEntity().getContent();

						String value = StreamTool.decodeStream(in);
						//						goods_name_1.setText(value);
						//						Toast.makeText(BabyActivity.this, value, Toast.LENGTH_SHORT).show();
						//						//发消息
						//						Message msg = Message.obtain();
						//						msg.what = SUCCESS;
						//						msg.obj = value;
						//						handler.sendMessage(msg);


						ArrayList<String> mArray1;
						ArrayList<String> mArray2;
						HashMap<String, Object> hashMap=new HashMap<String, Object>();
						HashMap<String, Object> hashMap1=null;
						HashMap<String, Object> hashMap2;
						HashMap<String, Object> hashMap3;

						try {

							JSONTokener jsonParser = new JSONTokener(value);

							JSONObject person = (JSONObject) jsonParser.nextValue();

							JSONObject datas=person.getJSONObject("datas");
							//goods_name_1.setText("....."+datas);
							JSONObject goods_info = datas.getJSONObject("goods_info");
							String goods_image = datas.getString("goods_image");

							goods_name=goods_info.getString("goods_name");//名字
							goods_price=goods_info.getString("goods_price");//价格
							goods_marketprice=goods_info.getString("goods_marketprice");//市场价
							goods_salenum=goods_info.getString("goods_salenum");//销量

							hashMap3= new HashMap<String, Object>();//goods_info
							hashMap3.put("goods_name", goods_name);
							hashMap3.put("goods_price", goods_price);
							hashMap3.put("goods_marketprice", goods_marketprice);
							hashMap3.put("goods_salenum", goods_salenum);

							//										mArray1 = new ArrayList<String>();
							//										for (int i = 0; i < goods_image.length(); i++) {
							//											String index1 = goods_image.getString(i);
							//											mArray1.add(index1);//图片
							//										}

							hashMap2= new HashMap<String, Object>();//datas
							hashMap2.put("goods_info", hashMap3);
							hashMap2.put("goods_image", goods_image);
							//hashMap2.put("goods_image", mArray1);

							hashMap1= new HashMap<String, Object>();//person总
							hashMap1.put("datas", hashMap2);
							//goods_name_1.setText("....."+hashMap1);
							//							{
							//								goods_name_1.setText(goods_name);goods_name_1.setText("ccccc");
							//								goods_price_1.setText(goods_price);
							//								goods_item_2.setText(goods_salenum);
							//
							//							}


							//通知 ui 更新
							Message msg = Message.obtain();
							msg.what= SUCCESS;
							msg.obj =goods_name+"/，/"+goods_price+"/，/"+goods_marketprice+"/，/"+goods_salenum+"/，/"+goods_image;

							handler.sendMessage(msg);

						}catch (Exception e) {
							// 异常处理代码

						}
					}

				} catch (Exception e) {
					//					e.printStackTrace();
					//					Message msg = Message.obtain();
					//					msg.what = ERROR;
					//					handler.sendMessage(msg);
				}


			};

		}.start();
		//goods_name="1111111";
	}

	private Handler handler = new Handler(){

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
				case SUCCESS:

					//通知list 显示 数据

					String txt = (String)msg.obj;
					String[] temp=null;
					temp=txt.split("/，/");
					goods_name_1.setText(""+temp[0]);
					goods_price_1.setText("￥"+temp[1]);
					goods_item_1.setText("卖家包邮");
					goods_item_2.setText("月销  " + temp[3]+"笔");
					goods_item_3.setText("广州 发货");
					image=temp[4].split(",");
					initViewPager(image);
					baby_progress_circle.setVisibility(View.GONE);


					break;
				case ERROR:

					//弹土司
					Toast.makeText(BabyActivity.this, "出错了....", Toast.LENGTH_LONG).show();

					break;

				default:
					break;
			}
		};
	};


}
