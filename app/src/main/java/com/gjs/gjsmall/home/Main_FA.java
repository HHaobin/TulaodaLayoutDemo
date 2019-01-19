package com.gjs.gjsmall.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjs.gjsmall.R;
import com.gjs.gjsmall.cart.Cart2_F;
import com.gjs.gjsmall.goods.Goods_F;
import com.gjs.gjsmall.like.Like_F;
import com.gjs.gjsmall.user.User_F;
import com.gjs.gjsmall.utils.LogUtil;
import com.javis.mytools.IBtnCallListener;


/**
 * 整个程序最底层的框架Activity，所有的Fragment都是依赖于此Activity而存在的
 *
 * @author
 *
 */
public class Main_FA extends FragmentActivity
		implements OnClickListener, IBtnCallListener
{
	private TextView iv_menu_text_0,iv_menu_text_1,iv_menu_text_2,iv_menu_text_3,iv_menu_text_4;
	private TextView iv_menu_main_0,iv_menu_main_1,iv_menu_main_2,iv_menu_main_3,iv_menu_main_4;
	private int[]       bt_menu_main_id = { R.id.iv_menu_main_0, R.id.iv_menu_main_1, R.id.iv_menu_main_2, R.id.iv_menu_main_3, R.id.iv_menu_main_4 };
	// 界面底部的菜单按钮
	private ImageView[] bt_menu    = new ImageView[5];
	// 界面底部的菜单按钮id
	private int[]       bt_menu_id = { R.id.iv_menu_0, R.id.iv_menu_1, R.id.iv_menu_2, R.id.iv_menu_3, R.id.iv_menu_4 };

	// 界面底部的选中菜单按钮资源
	private int[] select_on = { R.drawable.guide_home_on, R.drawable.guide_tfaccount_on, R.drawable.guide_discover_on, R.drawable.guide_cart_on, R.drawable.guide_account_on };
	// 界面底部的未选中菜单按钮资源
	private int[] select_off = { R.drawable.bt_menu_0_select, R.drawable.bt_menu_1_select, R.drawable.bt_menu_2_select, R.drawable.bt_menu_3_select, R.drawable.bt_menu_4_select };

	/** 主界面 */
	private Home_F  home_F;
	/** 分类界面 */
	private Goods_F mGoods_F;
	/** 喜欢界面 */
	private Like_F  mLike_F;
//	/** 购物车界面 */
//	private Cart_F  cart_F;
	/** 个人界面 */
	private User_F  user_F;
	/** 购物车界面 */
	private Cart2_F  cart2_F;
	private TextView net_no;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_fa);
		getSaveData();
		initView();

//		//判断是否有网络
//		if (isNetworkAvailable(this)) {
//			Toast.makeText(this.getApplicationContext(), "当前有可用网络！", Toast.LENGTH_LONG).show();
//			net_no.setVisibility(View.GONE);
//		}
//		else {
//			Toast.makeText(this.getApplicationContext(), "当前没有可用网络！", Toast.LENGTH_LONG).show();
//			net_no.setVisibility(View.VISIBLE);
//		}
	}


	private void getSaveData() {
		// 得到保存的购物车数据
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//
//		SharedPreferences sp = getSharedPreferences("SAVE_CART", Context.MODE_PRIVATE);
//		int size = sp.getInt("ArrayCart_size", 0);
//		for (int i = 0; i < size; i++) {
//			hashMap.put("type", sp.getString("ArrayCart_type_" + i, ""));
//			hashMap.put("color", sp.getString("ArrayCart_color_" + i, ""));
//			hashMap.put("num", sp.getString("ArrayCart_num_" + i, ""));
//			Data.arrayList_cart.add(hashMap);
//		}

	}

	// 初始化组件
	private void initView() {
		//net
		net_no= (TextView) findViewById(R.id.net_no);
		//text
		iv_menu_text_0= (TextView) findViewById(R.id.iv_menu_text_0);
		iv_menu_text_1= (TextView) findViewById(R.id.iv_menu_text_1);
		iv_menu_text_2= (TextView) findViewById(R.id.iv_menu_text_2);
		iv_menu_text_3= (TextView) findViewById(R.id.iv_menu_text_3);
		iv_menu_text_4= (TextView) findViewById(R.id.iv_menu_text_4);

		//text
		iv_menu_main_0= (TextView) findViewById(R.id.iv_menu_main_0);
		iv_menu_main_1= (TextView) findViewById(R.id.iv_menu_main_1);
		iv_menu_main_2= (TextView) findViewById(R.id.iv_menu_main_2);
		iv_menu_main_3= (TextView) findViewById(R.id.iv_menu_main_3);
		iv_menu_main_4= (TextView) findViewById(R.id.iv_menu_main_4);


		//text
		iv_menu_main_0.setOnClickListener(this);
		iv_menu_main_1.setOnClickListener(this);
		iv_menu_main_2.setOnClickListener(this);
		iv_menu_main_3.setOnClickListener(this);
		iv_menu_main_4.setOnClickListener(this);

		// 找到底部菜单的按钮并设置监听
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i] = (ImageView) findViewById(bt_menu_id[i]);
			bt_menu[i].setOnClickListener(this);
		}

		// 初始化默认显示的界面
		if (home_F == null) {
			home_F = new Home_F();
			addFragment(home_F);
			showFragment(home_F);
		} else {
			showFragment(home_F);
		}
		// 设置默认首页为点击时的图片
		bt_menu[0].setImageResource(select_on[0]);
		bt_menu[0].setImageResource(select_on[0]);
		textcolor();
		iv_menu_text_0.setTextColor(Color.rgb(255, 0, 0));
	}
	public void textcolor(){
		iv_menu_text_0.setTextColor(Color.rgb(255, 255, 255));
		iv_menu_text_1.setTextColor(Color.rgb(255, 255, 255));
		iv_menu_text_2.setTextColor(Color.rgb(255, 255, 255));
		iv_menu_text_3.setTextColor(Color.rgb(255, 255, 255));
		iv_menu_text_4.setTextColor(Color.rgb(255, 255, 255));
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.iv_menu_0:

				break;
			case R.id.iv_menu_1:

				break;
			case R.id.iv_menu_2:


				break;
			case R.id.iv_menu_3:

				break;
			case R.id.iv_menu_4:

				break;
			case R.id.iv_menu_main_0:
				textcolor();
				iv_menu_text_0.setTextColor(Color.rgb(255, 0, 0));
				// 主界面
				if (home_F == null) {
					home_F = new Home_F();
					// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
					addFragment(home_F);
					showFragment(home_F);
				} else {
					if (home_F.isHidden()) {
						showFragment(home_F);
					}
				}

				break;
			case R.id.iv_menu_main_1:
				textcolor();
				iv_menu_text_1.setTextColor(Color.rgb(255, 0, 0));
				// 货源界面
				if (mGoods_F == null) {
					mGoods_F = new Goods_F();
					// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
					if (!mGoods_F.isHidden()) {
						addFragment(mGoods_F);
						showFragment(mGoods_F);
					}
				} else {
					if (mGoods_F.isHidden()) {
						showFragment(mGoods_F);
					}
				}

				break;
			case R.id.iv_menu_main_2:
				textcolor();
				iv_menu_text_2.setTextColor(Color.rgb(255, 0, 0));
				// 分享界面
				if (mLike_F == null) {
					mLike_F = new Like_F();
					// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
					if (!mLike_F.isHidden()) {
						addFragment(mLike_F);
						showFragment(mLike_F);
					}
				} else {
					if (mLike_F.isHidden()) {
						showFragment(mLike_F);
					}
				}

				break;
			case R.id.iv_menu_main_3:
				textcolor();
				iv_menu_text_3.setTextColor(Color.rgb(255, 0, 0));
				// 购物车界面
				if (cart2_F == null) {
					cart2_F = new Cart2_F();
					// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
					if (!cart2_F.isHidden()) {
						addFragment(cart2_F);
						showFragment(cart2_F);
					}
				} else {
					if (cart2_F.isHidden()) {
						//重新加载
						removeFragment(cart2_F);
						cart2_F = new Cart2_F();
						addFragment(cart2_F);
						showFragment(cart2_F);
					}
				}

				break;
			case R.id.iv_menu_main_4:
				textcolor();
				iv_menu_text_4.setTextColor(Color.rgb(255, 0, 0));
				// 我的个人界面
				if (user_F == null) {
					user_F = new User_F();
					// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
					if (!user_F.isHidden()) {
						addFragment(user_F);
						showFragment(user_F);
					}
				} else {
					if (user_F.isHidden()) {
						//重新加载
						removeFragment(user_F);
						user_F = new User_F();
						addFragment(user_F);
						showFragment(user_F);
					}
				}
				break;
		}

		// 设置按钮的选中和未选中资源
		for (int i = 0; i < bt_menu_main_id.length; i++) {
			bt_menu[i].setImageResource(select_off[i]);
			if (v.getId() == bt_menu_main_id[i]) {
				bt_menu[i].setImageResource(select_on[i]);
			}
		}
	}

	/** 添加Fragment **/
	public void addFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		ft.add(R.id.show_layout, fragment);
		ft.commit();
	}
	/** 替换Fragment **/
	public void replaceFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.show_layout, fragment);
		ft.commit();
	}

	/** 删除Fragment **/
	public void removeFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	/** 显示Fragment **/
	public void showFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		// 设置Fragment的切换动画
		ft.setCustomAnimations(R.anim.cu_push_right_in, R.anim.cu_push_left_out);

		// 判断页面是否已经创建，如果已经创建，那么就隐藏掉
		if (home_F != null) {
			ft.hide(home_F);
		}
		if (mGoods_F != null) {
			ft.hide(mGoods_F);
		}
		if (mLike_F != null) {
			ft.hide(mLike_F);
		}
		if (cart2_F != null) {
			//Toast.makeText(this, fragment.toString(), Toast.LENGTH_SHORT).show();
			ft.hide(cart2_F);
		}
		if (user_F != null) {
			ft.hide(user_F);
		}

		ft.show(fragment);
		ft.commitAllowingStateLoss();

	}

	/** 返回按钮的监听 */
	@Override
	public void onBackPressed() {
		Toast.makeText(this, "点击返回按钮", Toast.LENGTH_SHORT).show();

		super.onBackPressed();
	}

	/** Fragment的回调函数 */
	@SuppressWarnings("unused")
	private IBtnCallListener btnCallListener;

	@Override
	public void onAttachFragment(Fragment fragment) {
		try {
			btnCallListener = (IBtnCallListener) fragment;
		} catch (Exception e) {
		}

		super.onAttachFragment(fragment);
	}

	/**
	 * 响应从Fragment中传过来的消息
	 */
	@Override
	public void transferMsg() {
		if (home_F == null) {
			home_F = new Home_F();
			addFragment(home_F);
			showFragment(home_F);
		} else {
			showFragment(home_F);
		}
		bt_menu[3].setImageResource(select_off[3]);
		bt_menu[0].setImageResource(select_on[0]);

		System.out.println("由Fragment中传送来的消息");
	}
/*
 * back事件
 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		LogUtil.d(LogUtil.getLogUtilsTag(Main_FA.class), " onKeyDown");

		if ((event.getKeyCode() == KeyEvent.KEYCODE_BACK)
				&& event.getAction() == KeyEvent.ACTION_UP) {
//			// dismiss PlusSubMenuHelper
//			if (mOverflowHelper != null && mOverflowHelper.isOverflowShowing()) {
//				mOverflowHelper.dismiss();
//				return true;
//			}
		}

		// 这里可以进行设置全局性的menu菜单的判断
		if ((event.getKeyCode() == KeyEvent.KEYCODE_BACK)
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			doTaskToBackEvent();
		}

		try {

			return super.dispatchKeyEvent(event);
		} catch (Exception e) {
			LogUtil.e(LogUtil.getLogUtilsTag(Main_FA.class),
					  "dispatch key event catch exception " + e.getMessage());
		}

		return false;
	}
	/**
	 * 返回隐藏到后台
	 */
	public void doTaskToBackEvent() {
		moveTaskToBack(true);

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
