package com.javis.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjs.gjsmall.Data.Data;
import com.gjs.gjsmall.R;
import com.gjs.gjsmall.cart.AllBaby_F;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;

public class Adapter_ListView_cart extends BaseAdapter {
	private Context context;
	private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
	private onCheckedChanged  listener;
	private SharedPreferences sp;
	String key;
	String path;
	String num;
	public Adapter_ListView_cart(Context context, ArrayList<HashMap<String, Object>> arrayList) {

		this.context = context;
		this.arrayList = arrayList;
	}

	public Adapter_ListView_cart(Context context) {
		this.context = context;

	}


	@Override
	public int getCount() {
		return (arrayList != null && arrayList.size() == 0) ? 0: arrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int position, View currentView, ViewGroup arg2) {
		HolderView holderView = new HolderView();;

		if (currentView == null) {
			//holderView = new HolderView();
			currentView = LayoutInflater.from(context).inflate(R.layout.adapter_listview_cart, null);
			holderView.tv_name = (TextView) currentView.findViewById(R.id.tv_name);
			holderView.tv_num = (TextView) currentView.findViewById(R.id.pop_num1);
			holderView.tv_reduce1 = (TextView) currentView.findViewById(R.id.pop_reduce1);
			holderView.tv_add1 = (TextView) currentView.findViewById(R.id.pop_add1);
			holderView.tv_price = (TextView) currentView.findViewById(R.id.tv_price);
			holderView.tv_type_color = (TextView) currentView.findViewById(R.id.tv_type_color);
			holderView.tv_store_name = (TextView) currentView.findViewById(R.id.store_name);
			holderView.iv_adapter_list_pic = (ImageView) currentView.findViewById(R.id.iv_adapter_list_pic);
			holderView.cb_choice = (CheckBox) currentView.findViewById(R.id.cb_choice);
			currentView.setTag(holderView);
//			num=""+holderView.tv_num;
			final HolderView finalHolderView = holderView;
			holderView.tv_reduce1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					String re_num="1";
					if(Integer.parseInt(arrayList.get(position).get("goods_num").toString().trim())>1) {
						re_num = Integer.parseInt(arrayList.get(position)
																  .get("goods_num")
																  .toString()
																  .trim()) - 1 + "";
						//Toast.makeText(context,re_num,Toast.LENGTH_LONG).show();
						reduce(position,re_num);
						finalHolderView.tv_num.setText(re_num);
						arrayList.get(position).put("goods_num",re_num);
						if(finalHolderView.cb_choice.isChecked()){
							Data.Allprice_cart -= Float.valueOf(arrayList.get(position).get("goods_price").toString().trim());
							//Toast.makeText(context,Data.Allprice_cart+"......."+finalHolderView.cb_choice.isChecked(),Toast.LENGTH_LONG).show();
							AllBaby_F.setAllprice("合计：￥"+Data.Allprice_cart + "");
						}
					}
					else {
						Toast.makeText(context,"若想删除，请点击编辑...",Toast.LENGTH_LONG).show();
					}

				}
			});
			holderView.tv_add1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					String re_num=Integer.parseInt(arrayList.get(position).get("goods_num").toString().trim())+1+"";
					//Toast.makeText(context,re_num,Toast.LENGTH_LONG).show();
					reduce(position,re_num);
					finalHolderView.tv_num.setText(re_num);
					arrayList.get(position).put("goods_num",re_num);
					if(finalHolderView.cb_choice.isChecked()){
						Data.Allprice_cart += Float.valueOf(arrayList.get(position).get("goods_price").toString().trim());
						//Toast.makeText(context,Data.Allprice_cart+"......."+finalHolderView.cb_choice.isChecked(),Toast.LENGTH_LONG).show();
						//tv_cart_Allprice.setText("合计：￥"+Data.Allprice_cart + "");
						AllBaby_F.setAllprice("合计：￥"+Data.Allprice_cart + "");
					}

				}
			});
		} else {
			holderView = (HolderView) currentView.getTag();
		}
		if (arrayList.size() != 0) {
			holderView.tv_name.setText("" + arrayList.get(position).get("goods_name"));//名字
			holderView.tv_num.setText("" + arrayList.get(position).get("goods_num"));//数量
			holderView.tv_price.setText("" + arrayList.get(position).get("goods_price"));//价钱
			holderView.tv_store_name.setText("" + arrayList.get(position).get("store_name"));//商店名称
			//holderView.tv_type_color.setText("类型:" + arrayList.get(position).get("type").toString() + "    颜色:" + arrayList.get(position).get("color").toString());
			holderView.cb_choice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean choice) {
					listener.getChoiceData(position, choice);
				}
			});


			//控件加载图片
			//显示图片的配置
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.ic_launcher)
					.showImageOnFail(R.drawable.ic_launcher)
					.cacheInMemory(true)
					.cacheOnDisk(true)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.build();
			ImageLoader.getInstance().displayImage("" + arrayList.get(position).get("goods_image_url"), holderView.iv_adapter_list_pic,options); // imageUrl代表图片的URL地址，imageView代表承载图片的IMAGEVIEW控件
		}
		return currentView;
	}

	public class HolderView {

		private ImageView iv_adapter_list_pic;
		private TextView tv_store_name;
		private TextView tv_price;
		private TextView tv_name;
		private TextView tv_type_color;
		private TextView tv_num;
		private CheckBox cb_choice;
		private TextView tv_reduce1;
		private TextView tv_add1;
	}
	
	public interface onCheckedChanged{
		
		public void getChoiceData(int position, boolean isChoice);
	}
	public void setOnCheckedChanged(onCheckedChanged listener){
		this.listener=listener;
	}



	private void reduce(int position,String re_num){

		sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		key =sp.getString("KEY",null);
		path="http://gjs.wsh68.com/mobile/index.php?act=member_cart&op=cart_edit_quantity";
		//你可以 选择 自己 进行 url 编码
		AsyncHttpClient client = new AsyncHttpClient();

		RequestParams params = new RequestParams();
		params.add("key", key);
		params.add("cart_id", ""+arrayList.get(position).get("cart_id"));
		params.add("quantity", re_num);
		//Toast.makeText(context, "post是 : " + re_num+"..."+arrayList.get(position).get("cart_id")+"..."+key, Toast.LENGTH_LONG).show();
		client.post(path, params,new AsyncHttpResponseHandler(){

			//访问 网络时, 服务器成功的处理了客户端的请求时 会被调用
			@Override
			public void onSuccess(int statusCode, Header[] headers,
								  byte[] responseBody) {

				//Toast.makeText(context, "post 登录的结果是 : " + new String(responseBody), Toast.LENGTH_LONG).show();
			}

			//访问 网络时, 请求失败会调用
			@Override
			public void onFailure(int statusCode, Header[] headers,
								  byte[] responseBody, Throwable error) {

				Toast.makeText(context, "对不起, 服务器无响应 ... ", Toast.LENGTH_LONG).show();
			}
		});

	}

}
