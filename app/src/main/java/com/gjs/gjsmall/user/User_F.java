package com.gjs.gjsmall.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjs.gjsmall.R;
import com.gjs.gjsmall.home.ExplainActivity;
import com.gjs.gjsmall.user.login.LoginActivity;
import com.gjs.gjsmall.user.setting.SettingActivity;

import java.io.IOException;
import java.net.URL;

/**
 * 个人主界面
 * @author
 *
 */
//implements OnClickListener
public class User_F extends Fragment implements View.OnClickListener

{
	//	private GridView my_gridView_user;
	//	private Adapter_GridView adapter_GridView;
	//	//资源文件
	//	private int[] pic_path={R.drawable.user_3,R.drawable.user_4,R.drawable.user_5,R.drawable.user_6,R.drawable.user_7};
	//
	//	private LinearLayout ll_user_life;
	//	private LinearLayout ll_user_members;
	//	private LinearLayout ll_user_store;
	//	private LinearLayout ll_user_opinion;
	//菜单
	private ImageView user_hander,user_message,setting_back;
	private TextView user_menu_0,user_menu_1,user_menu_2,user_name,user_set;
	private TextView user_menu_pic_6_1,user_menu_pic_6_4;
	private View user_menu_3, user_menu_4, user_menu_5,
			user_menu_6, user_menu_7, user_menu_8,
			user_menu_9, user_menu_10, user_menu_11;
	private SharedPreferences sp;
	Boolean login;
	private ImageView user_setting_message;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.user_f, null);
		initView(view);
		setting_back= (ImageView) view.findViewById(R.id.setting_back);
		user_setting_message = (ImageView) view.findViewById(R.id.user_setting_message);

		setting_back.setOnClickListener(this);
		user_setting_message.setOnClickListener(this);

		sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		login =sp.getBoolean("LOGIN",false);
		return view;
	}

	@Override
	public void onClick(View view) {
		Bundle bundle;
		switch (view.getId()) {
			case R.id.user_setting_message:
				Intent intent3=new Intent(getActivity(), ExplainActivity.class);
				//用Bundle携带数据
				bundle=new Bundle();
				//传递name参数为tinyphp
				bundle.putString("num", "3");
				bundle.putString("name", "我的消息");
				intent3.putExtras(bundle);
				startActivity(intent3);
				break;
			case R.id.setting_back:
				//进入设置界面
				Intent intent1 = new Intent(getActivity(), SettingActivity.class);
				startActivity(intent1);
				break;
			case R.id.user_hander:
				if(!login){
				//进入登录界面
				Intent intent2 = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent2);
				}
				break;
			case R.id.user_name:
				if(!login) {
					//进入登录界面
					Intent intent4 = new Intent(getActivity(), LoginActivity.class);
					startActivity(intent4);
				}
			default:
				break;
		}
	}


		private void initView(View view){
//
//		//		((TextView)view.findViewById(R.id.tv_top_txtTitle)).setText("我的淘宝");
//		//		ll_user_life=(LinearLayout) view.findViewById(R.id.ll_user_life);
//		//		ll_user_members=(LinearLayout) view.findViewById(R.id.ll_user_members);
//		//		ll_user_store=(LinearLayout) view.findViewById(R.id.ll_user_store);
//		//		ll_user_opinion=(LinearLayout) view.findViewById(R.id.ll_user_opinion);
//		//		ll_user_life.setOnClickListener(this);
//		//		ll_user_members.setOnClickListener(this);
//		//		ll_user_store.setOnClickListener(this);
//		//		ll_user_opinion.setOnClickListener(this);
//		//
//		//
//		//		my_gridView_user=(GridView)view.findViewById(R.id.gridView_user);
//		//		my_gridView_user.setSelector(new ColorDrawable(Color.TRANSPARENT));
//		//		adapter_GridView=new Adapter_GridView(getActivity(), pic_path);
//		//		my_gridView_user.setAdapter(adapter_GridView);
//		//		my_gridView_user.setOnItemClickListener(new OnItemClickListener() {
//		//
//		//			@Override
//		//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//		//									long arg3) {
//		//				//进入本机拥有传感器界面
//		//				Intent intent=new Intent(getActivity(),HelloSensor.class);
//		//				startActivity(intent);
//		//
//		//			}
//		//		});
//

		user_menu_pic_6_1 = (TextView) view.findViewById(R.id.user_menu_pic_6_1);
		user_menu_pic_6_4 = (TextView) view.findViewById(R.id.user_menu_pic_6_4);
		user_hander =  (ImageView) view.findViewById(R.id.user_hander);
//		user_message =  (ImageView) view.findViewById(R.id.user_message);
		user_name =  (TextView) view.findViewById(R.id.user_name);
//		user_set =  (TextView) view.findViewById(R.id.user_set);
//		user_menu_0 =  (TextView) view.findViewById(R.id.user_menu_0);
//		user_menu_1 =  (TextView) view.findViewById(R.id.user_menu_1);
//		user_menu_2 =  (TextView) view.findViewById(R.id.user_menu_2);
//		user_menu_3 =  view.findViewById(R.id.user_menu_3);
//		user_menu_4 =  view.findViewById(R.id.user_menu_4);
//		user_menu_5 =  view.findViewById(R.id.user_menu_5);
//		user_menu_6 =  view.findViewById(R.id.user_menu_6);
//		user_menu_7 =  view.findViewById(R.id.user_menu_7);
//		user_menu_8 =  view.findViewById(R.id.user_menu_8);
//		user_menu_9 =  view.findViewById(R.id.user_menu_9);
//		user_menu_10 =  view.findViewById(R.id.user_menu_10);
//		user_menu_11 =  view.findViewById(R.id.user_menu_11);
//
//


		user_menu_pic_6_1.setOnClickListener(this);
		user_menu_pic_6_4.setOnClickListener(this);
		user_hander.setOnClickListener(this);
//		user_message.setOnClickListener(this);
		user_name.setOnClickListener(this);
//		user_set.setOnClickListener(this);
//		user_menu_0.setOnClickListener(this);
//		user_menu_1.setOnClickListener(this);
//		user_menu_2.setOnClickListener(this);
//		user_menu_3.setOnClickListener(this);
//		user_menu_4.setOnClickListener(this);
//		user_menu_5.setOnClickListener(this);
//		user_menu_6.setOnClickListener(this);
//		user_menu_7.setOnClickListener(this);
//		user_menu_8.setOnClickListener(this);
//		user_menu_9.setOnClickListener(this);
//		user_menu_10.setOnClickListener(this);
//		user_menu_11.setOnClickListener(this);

		//获得实例对象
		sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		if(!sp.getString("IC_USERNAME", "").equals("")) {
			user_name.setText(sp.getString("IC_USERNAME", ""));
		}
		if(!sp.getString("IC_PRODEPOIT", "").equals("")) {
			user_menu_pic_6_1.setText(sp.getString("IC_PRODEPOIT", ""));
		}
		if(!sp.getString("IC_POINT", "").equals("")) {
			user_menu_pic_6_4.setText(sp.getString("IC_POINT", ""));
		}
		if(!sp.getString("IC_AVATOR", "").equals("")) {
				DownloadImageTask localDownloadImageTask = new DownloadImageTask(user_hander);
				String[]          arrayOfString          = new String[1];
				arrayOfString[0] = sp.getString("IC_AVATOR", "");
				localDownloadImageTask.execute(arrayOfString);
		}
	}
//
//	@Override
//	public void onClick(View arg0) {
//		switch (arg0.getId()) {
//			case R.id.user_menu_0:
//				//进入刮刮乐界面
//				Intent intent0=new Intent(getActivity(), LoginActivity.class);
//				startActivity(intent0);
//				break;
//			case R.id.user_menu_1:
//				//进入刮刮乐界面
//				Intent intent1=new Intent(getActivity(), User_life.class);
//				startActivity(intent1);
//				break;
//			case R.id.user_menu_2:
//				//进入刮刮乐界面
//				Intent intent2=new Intent(getActivity(), User_life.class);
//				startActivity(intent2);
//				break;
//			case R.id.user_menu_3:
//				//意见反馈界面
//				Intent intent3=new Intent(getActivity(), User_opinion.class);
//				startActivity(intent3);
//				break;
//			case R.id.user_menu_4:
//				//进入刮刮乐界面
//				Intent intent4=new Intent(getActivity(), User_life.class);
//				startActivity(intent4);
//				break;
//			case R.id.user_menu_5:
//				//进入刮刮乐界面
//				Intent intent5=new Intent(getActivity(), User_life.class);
//				startActivity(intent5);
//				break;
//			case R.id.user_menu_6:
//				//进入刮刮乐界面
//				Intent intent6=new Intent(getActivity(), User_life.class);
//				startActivity(intent6);
//				break;
//			case R.id.user_menu_7:
//				//意见反馈界面
//				Intent intent7=new Intent(getActivity(), User_opinion.class);
//				startActivity(intent7);
//				break;
//			case R.id.user_menu_8:
//				//进入刮刮乐界面
//				Intent intent8=new Intent(getActivity(), User_life.class);
//				startActivity(intent8);
//				break;
//			case R.id.user_menu_9:
//				//进入刮刮乐界面
//				Intent intent9=new Intent(getActivity(), User_life.class);
//				startActivity(intent9);
//				break;
//			case R.id.user_menu_10:
//				//进入刮刮乐界面
//				Intent intent10=new Intent(getActivity(), User_life.class);
//				startActivity(intent10);
//				break;
//			case R.id.user_menu_11:
//				//意见反馈界面
//				Intent intent11=new Intent(getActivity(), User_opinion.class);
//				startActivity(intent11);
//				break;
//			case R.id.user_hander:
//				//进入刮刮乐界面
//				Intent intent12=new Intent(getActivity(), User_life.class);
//				startActivity(intent12);
//				break;
//			case R.id.user_name:
//				//进入刮刮乐界面
//				Intent intent13=new Intent(getActivity(), User_life.class);
//				startActivity(intent13);
//				break;
//			case R.id.user_set:
//				//进入刮刮乐界面
//				Intent intent14=new Intent(getActivity(), User_life.class);
//				startActivity(intent14);
//				break;
//			case R.id.user_message:
//				//意见反馈界面
//				Intent intent15=new Intent(getActivity(), User_opinion.class);
//				startActivity(intent15);
//				break;
//			default:
//				break;
//		}
//
//	}
//
//
//	//	@Override
//	//	public void onClick(View arg0) {
//	//		switch (arg0.getId()) {
//	//			case R.id.ll_user_life:
//	//				//进入刮刮乐界面
//	//				Intent intent1=new Intent(getActivity(),User_life.class);
//	//				startActivity(intent1);
//	//				break;
//	//			case R.id.ll_user_members:
//	//				//进入刮刮乐界面
//	//				Intent intent2=new Intent(getActivity(),User_life.class);
//	//				startActivity(intent2);
//	//				break;
//	//			case R.id.ll_user_store:
//	//				//进入刮刮乐界面
//	//				Intent intent3=new Intent(getActivity(),User_life.class);
//	//				startActivity(intent3);
//	//				break;
//	//			case R.id.ll_user_opinion:
//	//				//意见反馈界面
//	//				Intent intent4=new Intent(getActivity(),User_opinion.class);
//	//				startActivity(intent4);
//	//				break;
//	//
//	//			default:
//	//				break;
//	//		}
//
//		}


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


}
