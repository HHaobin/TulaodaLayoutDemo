package com.gjs.gjsmall.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gjs.gjsmall.R;
import com.gjs.gjsmall.goods.assortment.Assortment_F_1;
import com.gjs.gjsmall.home.ExplainActivity;
import com.gjs.gjsmall.home.baby.WareActivity;

/**
 * 分类主界面
 * @author
 *
 */
public class Goods_F
		extends Fragment implements View.OnClickListener{

	private String toolsList[];
	private TextView toolsTextViews[];
	private View views[];
	private LayoutInflater inflater1;
	private ScrollView scrollView;
	private int scrllViewWidth = 0, scrollViewMiddle = 0;
	private ViewPager shop_pager;
	private int currentItem=0;
	private ShopAdapter shopAdapter;
	private Assortment_F_1     mAssortment_F_1 ;
	private TextView search_edit;
	private View goods_iv_refresh;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.goods_f, null);
		scrollView=(ScrollView) view.findViewById(R.id.tools_scrlllview);
		shopAdapter=new ShopAdapter(getActivity().getSupportFragmentManager());
		inflater1=inflater;//LayoutInflater.from(this.getActivity());
		showToolsView(view);
		initPager(view);

//		// 初始化默认显示的界面
//		if (mAssortment_F_1 == null) {
//			mAssortment_F_1 = new Assortment_F_1();
//			addFragment(mAssortment_F_1);
//			showFragment(mAssortment_F_1);
//		} else {
//			showFragment(mAssortment_F_1);
//		}
		goods_iv_refresh=view.findViewById(R.id.goods_iv_refresh);
		search_edit= (TextView) view.findViewById(R.id.search_edit);
		search_edit.setOnClickListener(this);
		goods_iv_refresh.setOnClickListener(this);
		return view;
	}

	/**
	 * 动态生成显示items中的textview
	 */
	private void showToolsView(View v) {
		toolsList=new String[]{"推荐分类","靓丽女装","品牌男装","日用百货","家用电器","电脑办公","酒水饮料","手机数码","母婴频道","个护化妆","食物生鲜","家居家纺","整车车品","鞋靴箱包","运动户外","图书","玩具乐器","钟表","居家生活","珠宝饰品","音像制品","家具建材","计生情趣","营养保健","奢侈礼品","生活服务","旅游出行"};
		LinearLayout toolsLayout=(LinearLayout) v.findViewById(R.id.tools);
		toolsTextViews=new TextView[toolsList.length];
		views=new View[toolsList.length];

		for (int i = 0; i < toolsList.length; i++) {
			View view=inflater1.inflate(R.layout.item_b_top_nav_layout, null);
			view.setId(i);
			view.setOnClickListener(toolsItemListener);
			TextView textView=(TextView) view.findViewById(R.id.text);
			textView.setText(toolsList[i]);
			toolsLayout.addView(view);
			toolsTextViews[i]=textView;
			views[i]=view;
		}
		changeTextColor(0);
	}

	private View.OnClickListener toolsItemListener =new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			shop_pager.setCurrentItem(v.getId());
//			//点击事件
//			switch (v.getId()) {
//				case 0:
//				if (mAssortment_F_1 == null) {
//					mAssortment_F_1 = new Assortment_F_1();
//					// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
//					addFragment(mAssortment_F_1);
//					showFragment(mAssortment_F_1);
//				} else {
//					if (mAssortment_F_1.isHidden()) {
//						showFragment(mAssortment_F_1);
//					}
//				}
//				case 1:
//					// 主界面
//					if (home_F == null) {
//						home_F = new Home_F();
//						// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
//						addFragment(home_F);
//						showFragment(home_F);
//					} else {
//						if (home_F.isHidden()) {
//							showFragment(home_F);
//						}
//					}
//					break;
//			}
		}
	};



	/**
	 * initPager<br/>
	 * 初始化ViewPager控件相关内容
	 */
	private void initPager(View v) {
		shop_pager=(ViewPager)v.findViewById(R.id.goods_pager);
		shop_pager.setAdapter(shopAdapter);
		shop_pager.setOnPageChangeListener(onPageChangeListener);
	}

	/**
	 * OnPageChangeListener<br/>
	 * 监听ViewPager选项卡变化事的事件
	 */

	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			if(shop_pager.getCurrentItem()!=arg0)shop_pager.setCurrentItem(arg0);
			if(currentItem!=arg0){
				changeTextColor(arg0);
				changeTextLocation(arg0);
			}
			currentItem=arg0;
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};

	@Override
	public void onClick(View v) {
		Bundle bundle;
		switch (v.getId()){
			case R.id.search_edit:
				//挑战到宝贝搜索界面
				Intent intent =new Intent(getActivity(), WareActivity.class);
				startActivity(intent);
				break;
			case R.id.goods_iv_refresh:
				Intent intent3=new Intent(getActivity(), ExplainActivity.class);
				//用Bundle携带数据
				bundle=new Bundle();
				//传递name参数为tinyphp
				bundle.putString("num", "3");
				bundle.putString("name", "我的消息");
				intent3.putExtras(bundle);
				startActivity(intent3);
		}
	}


	/**
	 * ViewPager 加载选项卡
	 * @author Administrator
	 *
	 */
	private class ShopAdapter extends FragmentPagerAdapter {
		public ShopAdapter(FragmentManager fm) {
			super(fm);
		}
		@Override
		public Fragment getItem(int arg0) {
//			Fragment fragment =new Fragment_pro_type();
//			Bundle bundle=new Bundle();
//			String str=toolsList[arg0];
//			bundle.putString("typename",str);
//			fragment.setArguments(bundle);
			Fragment fragment;
			Bundle bundle=new Bundle();
			String str = toolsList[arg0];
			bundle.putString("typename",str);

			switch (arg0){
				case 0:
					fragment=new Assortment_F_1();
					fragment.setArguments(bundle);
					return fragment;

				case 1:
					fragment=new Assortment_F_1();
					fragment.setArguments(bundle);
					return fragment;

				case 2:
					fragment=new Assortment_F_1();
					fragment.setArguments(bundle);
					return fragment;

				case 3:
					fragment=new Assortment_F_1();
					fragment.setArguments(bundle);
					return fragment;

				case 4:
					fragment=new Assortment_F_1();
					fragment.setArguments(bundle);
					return fragment;
				default:
					break;
			}
			fragment=new Assortment_F_1();
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public int getCount() {
			return toolsList.length;
		}
	}


	/**
	 * 改变textView的颜色
	 * @param id
	 */
	private void changeTextColor(int id) {
		for (int i = 0; i < toolsTextViews.length; i++) {
			if(i!=id){
				toolsTextViews[i].setBackgroundResource(android.R.color.transparent);
				toolsTextViews[i].setTextColor(0xff000000);
			}
		}
		toolsTextViews[id].setBackgroundResource(android.R.color.white);
		toolsTextViews[id].setTextColor(0xffff5d5e);
	}


	/**
	 * 改变栏目位置
	 *
	 * @param clickPosition
	 */
	private void changeTextLocation(int clickPosition) {

		int x = (views[clickPosition].getTop() - getScrollViewMiddle() + (getViewheight(views[clickPosition]) / 2));
		scrollView.smoothScrollTo(0, x);
	}

	/**
	 * 返回scrollview的中间位置
	 *
	 * @return
	 */
	private int getScrollViewMiddle() {
		if (scrollViewMiddle == 0)
			scrollViewMiddle = getScrollViewheight() / 2;
		return scrollViewMiddle;
	}

	/**
	 * 返回ScrollView的宽度
	 *
	 * @return
	 */
	private int getScrollViewheight() {
		if (scrllViewWidth == 0)
			scrllViewWidth = scrollView.getBottom() - scrollView.getTop();
		return scrllViewWidth;
	}

	/**
	 * 返回view的宽度
	 *
	 * @param view
	 * @return
	 */
	private int getViewheight(View view) {
		return view.getBottom() - view.getTop();
	}
}
