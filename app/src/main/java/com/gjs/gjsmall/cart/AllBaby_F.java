package com.gjs.gjsmall.cart;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gjs.gjsmall.Data.Data;
import com.gjs.gjsmall.R;
import com.gjs.gjsmall.home.baby.BabyActivity;
import com.javis.Adapter.Adapter_ListView_cart;
import com.javis.Adapter.Adapter_ListView_cart.onCheckedChanged;
import com.javis.mytools.IBtnCallListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 购物车界面中的全部宝贝界面
 * @author
 *
 */
@SuppressLint("ValidFragment")
public class AllBaby_F extends Fragment
		implements IBtnCallListener, onCheckedChanged, OnClickListener
{
	private static TextView tv_cart_Allprice;
	IBtnCallListener btnCallListener;
	private TextView tv_goShop, tv_cart_buy_Ordel;
	private LinearLayout          ll_cart;
	private ListView              listView_cart;
	private CheckBox              cb_cart_all;
	private Adapter_ListView_cart adapter;
	private String str_del = "结算(0)";
	private boolean[] is_choice;

	private SharedPreferences sp;
	String key;
	String path;
	ArrayList<HashMap<String, Object>> arrayList =new ArrayList<HashMap<String, Object>>();
	private Bundle bundle;
	RelativeLayout allbaby_progress_circle;

	public AllBaby_F(String del) {
		str_del = del;
	}

	public AllBaby_F() {
	}

	public static void setAllprice(String da){
		tv_cart_Allprice.setText(da);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.cart_all_f, null);
		initView0(view);
		//判断是否有网络
		if (isNetworkAvailable(getActivity())) {
			Toast.makeText(getActivity().getApplicationContext(), "当前有可用网络！", Toast.LENGTH_LONG).show();
			goCart(view);
		}
		else {
			Toast.makeText(getActivity().getApplicationContext(), "当前没有可用网络！", Toast.LENGTH_LONG).show();

		}

//		Toast.makeText(getActivity(), "刷新購物車..."+arrayList, Toast.LENGTH_SHORT).show();
//		is_choice=new boolean[arrayList.size()];
//		initView(view);

		return view;
	}

	private void initView0(View view) {
		tv_goShop = (TextView) view.findViewById(R.id.tv_goShop);
		tv_cart_Allprice = (TextView) view.findViewById(R.id.tv_cart_Allprice);
		tv_cart_buy_Ordel = (TextView) view.findViewById(R.id.tv_cart_buy_or_del);
		allbaby_progress_circle=(RelativeLayout)view.findViewById(R.id.allbaby_progress_circle);
		allbaby_progress_circle.setVisibility(View.VISIBLE);
		tv_cart_buy_Ordel.setText(str_del);

		ll_cart = (LinearLayout) view.findViewById(R.id.ll_cart);

		listView_cart = (ListView) view.findViewById(R.id.listView_cart);
		listView_cart.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent2 = new Intent(getActivity(), BabyActivity.class);
				//用Bundle携带数据
				bundle=new Bundle();
				//传递name参数为tinyphp
				bundle.putInt("num",  Integer.parseInt(arrayList.get(arg2).get("goods_id").toString()));
				intent2.putExtras(bundle);
				startActivity(intent2);
			}
		});


		tv_cart_buy_Ordel.setOnClickListener(this);
		tv_goShop.setOnClickListener(this);
	}
	private void initView(View view) {
		cb_cart_all = (CheckBox) view.findViewById(R.id.cb_cart_all);
		is_choice=new boolean[arrayList.size()];
		cb_cart_all.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				/*
				 * 判断一：（全选按钮选中）全选按钮是否选择，如果选择，那么列表每一行都选中
				 * 判断二：（全选按钮取消）当取消全选按钮时，会有两种情况
				 * ，第一：主动点击全选按钮，此时直接取消列表所有的选中状态，第二：取消列表某一行，导致全选取消，此时列表其他行仍然是选中
				 *
				 * 判断二的分析：（主动取消）判断列表每一行的选中状态，如果全部都是选中状态，那么（列表选中数=列表总数），此时属于主动取消，
				 * 则取消所有行的选中状态，反之（被动取消）则不响应
				 */

				// 记录列表每一行的选中状态数量
				int isChoice_all = 0;
				if (arg1) {
					// 设置全选
					for (int i = 0; i < arrayList.size(); i++) {
						// 如果选中了全选，那么就将列表的每一行都选中
						((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(true);
					}
				} else {
					// 设置全部取消
					for (int i = 0; i < arrayList.size(); i++) {
						// 判断列表每一行是否处于选中状态，如果处于选中状态，则计数+1
						if (((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).isChecked()) {
							// 计算出列表选中状态的数量
							isChoice_all += 1;
						}
					}
					// 判断列表选中数是否等于列表的总数，如果等于，那么就需要执行全部取消操作
					if (isChoice_all == arrayList.size()) {
						// 如果没有选中了全选，那么就将列表的每一行都不选
						for (int i = 0; i < arrayList.size(); i++) {
							// 列表每一行都取消
							((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(false);
						}
					}
				}
			}
		});


		// 如果购物车中有数据，那么就显示数据，否则显示默认界面
		if (arrayList != null && arrayList.size() != 0) {
			adapter = new Adapter_ListView_cart(getActivity(), arrayList);
			adapter.setOnCheckedChanged(this);
			listView_cart.setAdapter(adapter);
			ll_cart.setVisibility(View.GONE);

		} else {
			ll_cart.setVisibility(View.VISIBLE);
			allbaby_progress_circle.setVisibility(View.GONE);
		}


	}


	@Override
	public void onAttach(Activity activity) {
		btnCallListener = (IBtnCallListener) activity;

		super.onAttach(activity);
	}

	@Override
	public void transferMsg() {
		// 这里响应在FragmentActivity中的控件交互
		System.out.println("由Activity中传送来的消息");
	}

	/** adapter的回调函数，当点击CheckBox的时候传递点击位置和checkBox的状态 */
	@Override
	public void getChoiceData(int position, boolean isChoice) {

		//得到点击的哪一行
		if (isChoice) {
			if (arrayList.size()!=0) {
				//49表示商品的价格，这里偷了下懒，没有去动态获取商品价格
				Data.Allprice_cart += Float.valueOf(arrayList.get(position).get("goods_num").toString())*Float.valueOf(arrayList.get(position).get("goods_price").toString());
				//Toast.makeText(getActivity(), ".........1"+Data.Allprice_cart, Toast.LENGTH_SHORT).show();

			}
		} else {
			if (arrayList.size()!=0) {
				Data.Allprice_cart -= Float.valueOf(arrayList.get(position).get("goods_num").toString())*Float.valueOf(arrayList.get(position).get("goods_price").toString());
				//Toast.makeText(getActivity(), ".........1"+Data.Allprice_cart, Toast.LENGTH_SHORT).show();

			}
		}
		// 记录列表处于选中状态的数量
		int num_choice = 0;
		for (int i = 0; i < arrayList.size(); i++) {
			// 判断列表中每一行的选中状态，如果是选中，计数加1
			if (null!=listView_cart.getChildAt(i)&&((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).isChecked()) {
				// 列表处于选中状态的数量+1
				num_choice += 1;
				is_choice[i]=true;
			}
		}
		// 判断列表中的CheckBox是否全部选择
		if (num_choice == arrayList.size()) {
			// 如果选中的状态数量=列表的总数量，那么就将全选设置为选中
			cb_cart_all.setChecked(true);
		} else {
			// 如果选中的状态数量！=列表的总数量，那么就将全选设置为取消
			cb_cart_all.setChecked(false);
		}

		tv_cart_Allprice.setText("合计：￥"+Data.Allprice_cart + "");

		System.out.println("选择的位置--->"+position);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tv_goShop://点击去逛逛
				btnCallListener.transferMsg();
				break;
			case R.id.tv_cart_buy_or_del://点击结算/删除
				boolean[] is_choice_copy=is_choice;
				if (tv_cart_buy_Ordel.getText().toString().equals("删除")) {
					//执行删除操作
					if (arrayList.size()!=0) {
						for (int i = is_choice_copy.length-1; i >=0; i--) {
							if (is_choice_copy[i]) {
								((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(false);
								String id=arrayList.get(i).get("cart_id").toString().trim();
								is_choice_copy=deleteByIndex(is_choice, i);
								arrayList.remove(i);
								sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
								key =sp.getString("KEY",null);
								path="http://gjs.wsh68.com/mobile/index.php?act=member_cart&op=cart_del";

								//你可以 选择 自己 进行 url 编码
								AsyncHttpClient client = new AsyncHttpClient();

								RequestParams params = new RequestParams();
								params.add("key", key);
								params.add("cart_id", id);

								client.post(path, params,new AsyncHttpResponseHandler(){

									//访问 网络时, 服务器成功的处理了客户端的请求时 会被调用
									@Override
									public void onSuccess(int statusCode, Header[] headers,
														  byte[] responseBody) {

										//Toast.makeText(getActivity(), "post 登录的结果是 : " + new String(responseBody), Toast.LENGTH_LONG).show();
										Toast.makeText(getActivity(), "刪除成功" , Toast.LENGTH_LONG).show();
									}

									//访问 网络时, 请求失败会调用
									@Override
									public void onFailure(int statusCode, Header[] headers,
														  byte[] responseBody, Throwable error) {

										Toast.makeText(getActivity(), "对不起, 服务器无响应 ... ", Toast.LENGTH_LONG).show();
									}
								});


							}
						}
					}


					if (arrayList.size()==0) {
						ll_cart.setVisibility(View.VISIBLE);
					}

					adapter.notifyDataSetChanged();
					is_choice=new boolean[arrayList.size()];
					System.out.println("此时的长度---->"+is_choice.length);
				}else {
					//执行结算操作
					Toast.makeText(getActivity(), "暂时无法结算", Toast.LENGTH_SHORT).show();
				}

				break;
			default:
				break;
		}

	}


	/**删除数组中的一个元素*/
	public static boolean[] deleteByIndex(boolean[] array, int index) {
		boolean[] newArray = new boolean[array.length - 1];
		for (int i = 0; i < newArray.length; i++) {
			if (i < index) {
				newArray[i] = array[i];
			} else {
				newArray[i] = array[i + 1];
			}
		}
		return newArray;
	}


	//添加到购物车的 请求方法
	public void goCart(final View view){
		//path = getResources().getString(R.string.ip);
		path="http://gjs.wsh68.com/mobile/index.php?act=member_cart&op=cart_list";
		//获得实例对象
		sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		key =sp.getString("KEY",null);
//		Toast.makeText(getActivity(), key, Toast.LENGTH_SHORT).show();
		//		判断 是否为空
		if(TextUtils.isEmpty(key)){
			Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
			ll_cart.setVisibility(View.VISIBLE);
			return;
		} else{
			ll_cart.setVisibility(View.GONE);
		}



		//你可以 选择 自己 进行 url 编码
		AsyncHttpClient client = new AsyncHttpClient();

		RequestParams params = new RequestParams();
		params.add("key", key);
		client.post(path, params,new AsyncHttpResponseHandler(){

			//访问 网络时, 服务器成功的处理了客户端的请求时 会被调用
			@Override
			public void onSuccess(int statusCode, Header[] headers,
								  byte[] responseBody) {

				//Toast.makeText(getActivity(), "post 登录的结果是 : " + new String(responseBody), Toast.LENGTH_LONG).show();
				cartResult(new String(responseBody),view);
			}

			//访问 网络时, 请求失败会调用
			@Override
			public void onFailure(int statusCode, Header[] headers,
								  byte[] responseBody, Throwable error) {

				Toast.makeText(getActivity(), "对不起, 服务器无响应 ... ", Toast.LENGTH_LONG).show();
			}
		});




//		Toast.makeText(getActivity(), ".........5", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 登录之后的获取 方法
	 * @param value
	 *
	 *
	 */
	public void cartResult(String value,View view){


		try {

			JSONTokener jsonParser = new JSONTokener(value);

			// 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
			// 如果此时的读取位置在"name" : 了，那么nextValue就是"yuanzhifei89"（String）
			JSONObject person = (JSONObject) jsonParser.nextValue();

			// 接下来的就是JSON对象的操作了
			//person.getJSONArray("phone");
			//person.getString("code");
			//person.getInt("age");
			String code=person.getString("code");
			JSONObject datas=person.getJSONObject("datas");
			//person.getBoolean("married");

			JSONArray member_info = datas.getJSONArray("cart_list");
			//String ic_available_rc_balance = member_info.getString("available_rc_balance");
			int i;
			for(i=0;i<member_info.length();i++){
				JSONObject jo = (JSONObject) member_info.get(i);
				String cart_id    = jo.getString("cart_id");//
				String buyer_id     = jo.getString("buyer_id");//
				String store_id = jo.getString("store_id");//
				String store_name    = jo.getString("store_name");//
				String goods_id     = jo.getString("goods_id");//
				String goods_name = jo.getString("goods_name");//
				String goods_price    = jo.getString("goods_price");//
				String goods_num     = jo.getString("goods_num");//
				String goods_image = jo.getString("goods_image");//
				String bl_id     = jo.getString("bl_id");//
				String integral = jo.getString("integral");//
				String withh     = jo.getString("withh");//
				String goodss_price = jo.getString("goodss_price");//
				String goods_image_url     = jo.getString("goods_image_url");//
				String goods_sum = jo.getString("goods_sum");//

				HashMap<String, Object> mHashMap = new HashMap<String, Object>();
				mHashMap.put("cart_id",cart_id);
				mHashMap.put("buyer_id",buyer_id);
				mHashMap.put("store_id",store_id);
				mHashMap.put("store_name",store_name);
				mHashMap.put("goods_id",goods_id);
				mHashMap.put("goods_name",goods_name);
				mHashMap.put("goods_price",goods_price);
				mHashMap.put("goods_num",goods_num);
				mHashMap.put("goods_image",goods_image);
				mHashMap.put("bl_id",bl_id);
				mHashMap.put("integral",integral);
				mHashMap.put("withh",withh);
				mHashMap.put("goodss_price",goodss_price);
				mHashMap.put("goods_image_url",goods_image_url);
				mHashMap.put("goods_sum",goods_sum);

				arrayList.add(mHashMap);
				allbaby_progress_circle.setVisibility(View.GONE);
			}

			//Toast.makeText(getActivity(), "成功"+arrayList, Toast.LENGTH_LONG).show();
			initView(view);

		}catch (Exception e) {
			// 异常处理代码

			//			try {
			//
			//				JSONTokener jsonParser = new JSONTokener(value);
			//
			//				// 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
			//				// 如果此时的读取位置在"name" : 了，那么nextValue就是"yuanzhifei89"（String）
			//				JSONObject person = null;
			//				person = (JSONObject) jsonParser.nextValue();
			//				// 接下来的就是JSON对象的操作了
			//				//person.getJSONArray("phone");
			//				//person.getString("code");
			//				//person.getInt("age");
			//				String code=person.getString("code");
			//				String login=person.getString("login");
			//				JSONObject datas=person.getJSONObject("datas");
			//				//person.getBoolean("married");
			//				String ic_error = datas.getString("error");
			//				Toast.makeText(LoginActivity.this, ic_error, Toast.LENGTH_LONG).show();
			//			} catch (JSONException e1) {
			//				e1.printStackTrace();
			//			}

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

}
