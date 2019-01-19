package com.gjs.MyView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.gjs.gjsmall.Data.Data;
import com.gjs.gjsmall.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 宝贝详情界面的弹窗
 * @author http://yecaoly.taobao.com
 *
 */
@SuppressLint("CommitPrefEdits")
public class BabyPopWindow implements OnDismissListener, OnClickListener {
	private TextView pop_choice_16g, pop_choice_32g, pop_choice_16m, pop_choice_32m, pop_choice_black, pop_choice_white, pop_add, pop_reduce, pop_num, pop_ok;
	private ImageView pop_del;

	private PopupWindow         popupWindow;
	private OnItemClickListener listener;
	private final int ADDORREDUCE = 1;
	private Context context;
	/**保存选择的颜色的数据*/
	private String str_color = "";
	/**保存选择的类型的数据*/
	private String str_type  = "";
	private SharedPreferences sp;
	String key;
	String path;
	ArrayList<HashMap<String, Object>> arrayList =new ArrayList<HashMap<String, Object>>();
	private  ImageView iv_adapter_grid_pic;
	private TextView iv_adapter_grid_price,iv_adapter_grid_ku;

	public BabyPopWindow(Context context) {
		this.context = context;
		View view = LayoutInflater.from(context)
								  .inflate(R.layout.adapter_popwindow, null);
		iv_adapter_grid_pic = (ImageView) view.findViewById(R.id.iv_adapter_grid_pic);
		iv_adapter_grid_price = (TextView) view.findViewById(R.id.iv_adapter_grid_price);
		iv_adapter_grid_ku = (TextView) view.findViewById(R.id.iv_adapter_grid_ku);
		pop_choice_16g = (TextView) view.findViewById(R.id.pop_choice_16g);
		pop_choice_32g = (TextView) view.findViewById(R.id.pop_choice_32g);
		pop_choice_16m = (TextView) view.findViewById(R.id.pop_choice_16m);
		pop_choice_32m = (TextView) view.findViewById(R.id.pop_choice_32m);
		pop_choice_black = (TextView) view.findViewById(R.id.pop_choice_black);
		pop_choice_white = (TextView) view.findViewById(R.id.pop_choice_white);
		pop_add = (TextView) view.findViewById(R.id.pop_add);
		pop_reduce = (TextView) view.findViewById(R.id.pop_reduce);
		pop_num = (TextView) view.findViewById(R.id.pop_num);
		pop_ok = (TextView) view.findViewById(R.id.pop_ok);
		pop_del = (ImageView) view.findViewById(R.id.pop_del);

		pop_choice_16g.setOnClickListener(this);
		pop_choice_32g.setOnClickListener(this);
		pop_choice_16m.setOnClickListener(this);
		pop_choice_32m.setOnClickListener(this);
		pop_choice_black.setOnClickListener(this);
		pop_choice_white.setOnClickListener(this);
		pop_add.setOnClickListener(this);
		pop_reduce.setOnClickListener(this);
		pop_ok.setOnClickListener(this);
		pop_del.setOnClickListener(this);


		//Toast.makeText(context, "工工整整的代码最好了", Toast.LENGTH_SHORT).show();
		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		//设置popwindow的动画效果
		popupWindow.setAnimationStyle(R.style.popWindow_anim_style);
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		popupWindow.setOnDismissListener(this);// 当popWindow消失时的监听

		iv_adapter_grid_price.setText("￥"+Data.baby_goods_price);
		iv_adapter_grid_ku.setText("库存  "+Data.baby_goods_ku);

		//控件加载图片
		//显示图片的配置
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
		ImageLoader.getInstance().displayImage(Data.baby_goods_pic, iv_adapter_grid_pic, options);
	}


	public interface OnItemClickListener {
		/** 设置点击确认按钮时监听接口 */
		public void onClickOKPop();
	}

	/**设置监听*/
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.listener = listener;
	}


	// 当popWindow消失时响应
	@Override
	public void onDismiss() {

	}

	/**弹窗显示的位置*/
	public void showAsDropDown(View parent) {
		popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
	}

	/**消除弹窗*/
	public void dissmiss() {
		popupWindow.dismiss();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.pop_choice_16g:

				pop_choice_16g.setBackgroundResource(R.drawable.yuanjiao_choice);
				pop_choice_32g.setBackgroundResource(R.drawable.yuanjiao);
				pop_choice_16m.setBackgroundResource(R.drawable.yuanjiao);
				pop_choice_32m.setBackgroundResource(R.drawable.yuanjiao);
				str_type = pop_choice_16g.getText()
										 .toString();
				break;
			case R.id.pop_choice_32g:
				pop_choice_16g.setBackgroundResource(R.drawable.yuanjiao);
				pop_choice_32g.setBackgroundResource(R.drawable.yuanjiao_choice);
				pop_choice_16m.setBackgroundResource(R.drawable.yuanjiao);
				pop_choice_32m.setBackgroundResource(R.drawable.yuanjiao);

				str_type = pop_choice_32g.getText()
										 .toString();
				break;
			case R.id.pop_choice_16m:

				pop_choice_16g.setBackgroundResource(R.drawable.yuanjiao);
				pop_choice_32g.setBackgroundResource(R.drawable.yuanjiao);
				pop_choice_16m.setBackgroundResource(R.drawable.yuanjiao_choice);
				pop_choice_32m.setBackgroundResource(R.drawable.yuanjiao);
				str_type = pop_choice_16m.getText()
										 .toString();
				break;
			case R.id.pop_choice_32m:

				pop_choice_16g.setBackgroundResource(R.drawable.yuanjiao);
				pop_choice_32g.setBackgroundResource(R.drawable.yuanjiao);
				pop_choice_16m.setBackgroundResource(R.drawable.yuanjiao);
				pop_choice_32m.setBackgroundResource(R.drawable.yuanjiao_choice);

				str_type = pop_choice_32m.getText()
										 .toString();

				break;
			case R.id.pop_choice_black:

				pop_choice_black.setBackgroundResource(R.drawable.yuanjiao_choice);
				pop_choice_white.setBackgroundResource(R.drawable.yuanjiao);

				str_color = pop_choice_black.getText()
											.toString();
				break;
			case R.id.pop_choice_white:

				pop_choice_black.setBackgroundResource(R.drawable.yuanjiao);
				pop_choice_white.setBackgroundResource(R.drawable.yuanjiao_choice);

				str_color = pop_choice_white.getText()
											.toString();
				break;
			case R.id.pop_add:
				if (!pop_num.getText()
							.toString()
							.equals("750"))
				{

					String num_add = Integer.valueOf(pop_num.getText()
															.toString()) + ADDORREDUCE + "";
					pop_num.setText(num_add);
				} else {
					Toast.makeText(context, "不能超过最大产品数量", Toast.LENGTH_SHORT)
						 .show();
				}

				break;
			case R.id.pop_reduce:
				if (!pop_num.getText()
							.toString()
							.equals("1"))
				{
					String num_reduce = Integer.valueOf(pop_num.getText()
															   .toString()) - ADDORREDUCE + "";

					pop_num.setText(num_reduce);
				} else {
					Toast.makeText(context, "购买数量不能低于1件", Toast.LENGTH_SHORT)
						 .show();
				}
				break;
			case R.id.pop_del:
				listener.onClickOKPop();
				dissmiss();

				break;
			case R.id.pop_ok:
				listener.onClickOKPop();
				if (str_color.equals("")) {
					Toast.makeText(context, "亲，你还没有选择颜色哟~", Toast.LENGTH_SHORT)
						 .show();
				}
				//				else if (str_type.equals("")) {
				//					Toast.makeText(context, "亲，你还没有选择类型哟~", Toast.LENGTH_SHORT).show();
				//				}
				else {
					HashMap<String, Object> allHashMap = new HashMap<String, Object>();

					//allHashMap.put("color",str_color);
					allHashMap.put("type", str_type);
					allHashMap.put("num",
								   pop_num.getText()
										  .toString());
					allHashMap.put("id", Data.arrayList_cart_id);

					//Data.arrayList_cart.add(allHashMap);
					toCart();
					//setSaveData();
					dissmiss();
//					Toast.makeText(context, "成功添加到购物车", Toast.LENGTH_SHORT)
//						 .show();
				}
				break;

			default:
				break;
		}
	}

	/**保存购物车的数据*/
	private void setSaveData() {
		SharedPreferences sp     = context.getSharedPreferences("SAVE_CART", Context.MODE_PRIVATE);
		Editor            editor = sp.edit();
		editor.putInt("ArrayCart_size", Data.arrayList_cart.size());
		for (int i = 0; i < Data.arrayList_cart.size(); i++) {
			editor.remove("ArrayCart_type_" + i);
			editor.remove("ArrayCart_color_" + i);
			editor.remove("ArrayCart_num_" + i);
			editor.putString("ArrayCart_type_" + i,
							 Data.arrayList_cart.get(i)
												.get("type")
												.toString());
			editor.putString("ArrayCart_color_" + i,
							 Data.arrayList_cart.get(i)
												.get("color")
												.toString());
			editor.putString("ArrayCart_num_" + i,
							 Data.arrayList_cart.get(i)
												.get("num")
												.toString());

		}


	}

	//登录的 方法
	public void toCart() {
		//path = getResources().getString(R.string.ip);
		path = "http://gjs.wsh68.com/mobile/index.php?act=member_cart&op=cart_add";
		//获得实例对象
		sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		key = sp.getString("KEY", null);
//		Toast.makeText(context, key, Toast.LENGTH_SHORT)
//			 .show();
		//		//		判断 是否为空
		//		if(TextUtils.isEmpty(key)){
		//			Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
		//			return;
		//		}
		//		Toast.makeText(getActivity(), ".........1", Toast.LENGTH_SHORT).show();


		//你可以 选择 自己 进行 url 编码
		AsyncHttpClient client = new AsyncHttpClient();

		RequestParams params = new RequestParams();
		params.add("key", key);
		params.add("goods_id", ""+Data.arrayList_cart_id);
		params.add("quantity", pop_num.getText().toString());
		client.post(path, params, new AsyncHttpResponseHandler() {

			//访问 网络时, 服务器成功的处理了客户端的请求时 会被调用
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				String a=new String(responseBody)+"";
				Boolean boon= a.equals("{\"code\":200,\"datas\":\"1\"}");
				//Toast.makeText(context, a+"........"+boon, Toast.LENGTH_LONG).show();
				if(boon){
					Toast.makeText(context, "成功添加到购物车", Toast.LENGTH_LONG).show();
				}
				else{
					Toast.makeText(context, "请登录...", Toast.LENGTH_LONG).show();
				}
				//Toast.makeText(context, "post 登录的结果是 : " + new String(responseBody), Toast.LENGTH_LONG).show();
				//cartResult(new String(responseBody));
			}

			//访问 网络时, 请求失败会调用
			@Override
			public void onFailure(int statusCode, Header[] headers,
								  byte[] responseBody, Throwable error)
			{

				Toast.makeText(context, "对不起, 服务器无响应 ... ", Toast.LENGTH_LONG)
					 .show();
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
	public void cartResult(String value){


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



			}

			//Toast.makeText(getActivity(), "成功"+arrayList, Toast.LENGTH_LONG).show();


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
}