package com.gjs.gjsmall.home.points;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjs.gjsmall.R;
import com.javis.mytools.IBtnCallListener;


/**
 * 整个程序最底层的框架Activity，所有的Fragment都是依赖于此Activity而存在的
 *
 * @author
 *
 */
public class Point_FA
		extends FragmentActivity
		implements OnClickListener, IBtnCallListener
{
	private View point_menu_0,point_menu_1,point_menu_2;
	private TextView point_iv_menu_text_0,point_iv_menu_text_1,point_iv_menu_text_2;
	// 界面底部的菜单按钮
	private ImageView[] bt_menu    = new ImageView[3];
	// 界面底部的菜单按钮id
	private int[]       bt_menu_id = { R.id.point_iv_menu_0, R.id.point_iv_menu_1, R.id.point_iv_menu_2};

	// 界面底部的选中菜单按钮资源
	private int[] select_on = { R.drawable.point_menu_0_on, R.drawable.point_menu_1_on, R.drawable.point_menu_2_on };
	// 界面底部的未选中菜单按钮资源
	private int[] select_off = { R.drawable.point_menu_0_select, R.drawable.point_menu_1_select, R.drawable.point_menu_2_select };

	/** 1界面 */
	private Point_one_F  point_one_F;
	/** 2界面 */
	private Point_two_F point_two_F;
	/** 3界面 */
	private Point_three_F  point_three_F;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.point_fa);
		getSaveData();
		initView();
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
		//text
		point_iv_menu_text_0= (TextView) findViewById(R.id.point_iv_menu_text_0);
		point_iv_menu_text_1= (TextView) findViewById(R.id.point_iv_menu_text_1);
		point_iv_menu_text_2= (TextView) findViewById(R.id.point_iv_menu_text_2);
		point_menu_0=findViewById(R.id.point_menu_0);
		point_menu_1=findViewById(R.id.point_menu_1);
		point_menu_2=findViewById(R.id.point_menu_2);

		// 找到底部菜单的按钮并设置监听
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i] = (ImageView) findViewById(bt_menu_id[i]);
			bt_menu[i].setOnClickListener(this);
		}

		// 初始化默认显示的界面
		if (point_one_F == null) {
			point_one_F = new Point_one_F();
			addFragment(point_one_F);
			showFragment(point_one_F);
		} else {
			showFragment(point_one_F);
		}
		// 设置默认首页为点击时的图片
		bt_menu[0].setImageResource(select_on[0]);
		textcolor();
		point_iv_menu_text_0.setTextColor(Color.rgb(255, 255, 255));
		point_menu_0.setBackgroundColor(Color.rgb(102, 102, 102));
	}
	public void textcolor(){
		point_iv_menu_text_0.setTextColor(Color.rgb(0, 0, 0));
		point_iv_menu_text_1.setTextColor(Color.rgb(0, 0, 0));
		point_iv_menu_text_2.setTextColor(Color.rgb(0, 0, 0));
		point_menu_0.setBackgroundColor(Color.rgb(202, 202, 202));
		point_menu_1.setBackgroundColor(Color.rgb(202, 202, 202));
		point_menu_2.setBackgroundColor(Color.rgb(202, 202, 202));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.point_iv_menu_0:
				textcolor();
				point_iv_menu_text_0.setTextColor(Color.rgb(255, 255, 255));
				point_menu_0.setBackgroundColor(Color.rgb(102, 102, 102));
				// 积分界面
				if (point_one_F == null) {
					point_one_F = new Point_one_F();
					// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
					addFragment(point_one_F);
					showFragment(point_one_F);
				} else {
					if (point_one_F.isHidden()) {
						showFragment(point_one_F);
					}
				}

				break;
			case R.id.point_iv_menu_1:
				textcolor();
				point_iv_menu_text_1.setTextColor(Color.rgb(255, 255, 255));
				point_menu_1.setBackgroundColor(Color.rgb(102, 102, 102));
				// 界面
				if (point_two_F == null) {
					point_two_F = new Point_two_F();
					// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
					if (!point_two_F.isHidden()) {
						addFragment(point_two_F);
						showFragment(point_two_F);
					}
				} else {
					if (point_two_F.isHidden()) {
						showFragment(point_two_F);
					}
				}

				break;
			case R.id.point_iv_menu_2:
				textcolor();
				point_iv_menu_text_2.setTextColor(Color.rgb(255, 255, 255));
				point_menu_2.setBackgroundColor(Color.rgb(102, 102, 102));
				// 界面
				if (point_three_F == null) {
					point_three_F = new Point_three_F();
					// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
					if (!point_three_F.isHidden()) {
						addFragment(point_three_F);
						showFragment(point_three_F);
					}
				} else {
					if (point_three_F.isHidden()) {
						showFragment(point_three_F);
					}
				}

				break;

			case R.id.point_iv_menu_text_0:
				textcolor();
				point_iv_menu_text_0.setTextColor(Color.rgb(255, 255, 255));
				point_menu_0.setBackgroundColor(Color.rgb(102, 102, 102));
				// 积分界面
				if (point_one_F == null) {
					point_one_F = new Point_one_F();
					// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
					addFragment(point_one_F);
					showFragment(point_one_F);
				} else {
					if (point_one_F.isHidden()) {
						showFragment(point_one_F);
					}
				}

				break;
			case R.id.point_iv_menu_text_1:
				textcolor();
				point_iv_menu_text_1.setTextColor(Color.rgb(255, 255, 255));
				point_menu_1.setBackgroundColor(Color.rgb(102, 102, 102));
				// 界面
				if (point_two_F == null) {
					point_two_F = new Point_two_F();
					// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
					if (!point_two_F.isHidden()) {
						addFragment(point_two_F);
						showFragment(point_two_F);
					}
				} else {
					if (point_two_F.isHidden()) {
						showFragment(point_two_F);
					}
				}

				break;
			case R.id.point_iv_menu_text_2:
				textcolor();
				point_iv_menu_text_2.setTextColor(Color.rgb(255, 255, 255));
				point_menu_2.setBackgroundColor(Color.rgb(102, 102, 102));
				// 界面
				if (point_three_F == null) {
					point_three_F = new Point_three_F();
					// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
					if (!point_three_F.isHidden()) {
						addFragment(point_three_F);
						showFragment(point_three_F);
					}
				} else {
					if (point_three_F.isHidden()) {
						showFragment(point_three_F);
					}
				}

				break;

		}

		// 设置按钮的选中和未选中资源
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i].setImageResource(select_off[i]);
			if (v.getId() == bt_menu_id[i]) {
				bt_menu[i].setImageResource(select_on[i]);
			}
		}
	}

	/** 添加Fragment **/
	public void addFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		ft.add(R.id.point_show_layout, fragment);
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
		if (point_one_F != null) {
			ft.hide(point_one_F);
		}
		if (point_two_F != null) {
			ft.hide(point_two_F);
		}
		if (point_three_F != null) {
			ft.hide(point_three_F);
		}

		ft.show(fragment);
		ft.commitAllowingStateLoss();

	}

	/** 返回按钮的监听 */
	@Override
	public void onBackPressed() {
		//Toast.makeText(this, "点击返回按钮", Toast.LENGTH_SHORT).show();

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
		if (point_one_F == null) {
			point_one_F = new Point_one_F();
			addFragment(point_one_F);
			showFragment(point_one_F);
		} else {
			showFragment(point_one_F);
		}
		bt_menu[2].setImageResource(select_off[2]);
		bt_menu[0].setImageResource(select_on[0]);

		System.out.println("由Fragment中传送来的消息");
	}



}
