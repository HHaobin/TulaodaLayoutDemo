package com.gjs.gjsmall.splash;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gjs.gjsmall.home.Main_FA;
import com.gjs.gjsmall.utils.Constants;
import com.gjs.gjsmall.utils.DensityUtils;
import com.gjs.gjsmall.utils.PreferenceUtils;
import com.gjs.gjsmall.R;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @项目名: zh
 * @包名: org.itheima15.zhbj.activity
 * @类名: GuideUI
 * @作者: 黄浩彬
 * @创建时间: 2016-1-7 下午2:24:58
 * @描述: TODO:
 *
 * @更新时间: $Date$
 * @更新人: $Author$
 * @版本: $Rev$
 * @更新内容: TODO:
 */
public class GuideUI extends Activity
		implements OnClickListener
{
	private static final String TAG = "GuideUI";
	private ViewPager    mViewPager;
	private LinearLayout mPointContainer;
	private View         mPointSelected;
	private Button       mBtnStart;

	private List<ImageView> mDatas;

	private int[]				icons	= new int[] {
			R.drawable.guide_1,
			R.drawable.guide_2,
			R.drawable.guide_3
	};
	private int					mPointSpace;			// 两点间的距离

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// 去掉title
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_guide);

		// 初始化view
		initView();
		// 初始化数据
		initData();
	}

	private void initView()
	{
		mViewPager = (ViewPager) findViewById(R.id.guide_viewpager);
		mPointContainer = (LinearLayout) findViewById(R.id.guide_point_container);
		mPointSelected = findViewById(R.id.guide_point_selected);
		mBtnStart = (Button) findViewById(R.id.guide_btn_start);

		mBtnStart.setOnClickListener(this);
	}

	private void initData()
	{
		// 加载list数据
		mDatas = new ArrayList<ImageView>();
		for (int i = 0; i < icons.length; i++)
		{
			ImageView iv = new ImageView(this);
			iv.setImageResource(icons[i]);
			iv.setScaleType(ScaleType.FIT_XY);

			mDatas.add(iv);

			// 添加动态的点
			View point = new View(this);
			point.setBackgroundResource(R.drawable.point_normal);

			// xml布局中希望是dp值
			// code中，出现的宽高数据都是指的是px,dp-->px
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dp2px(this, 10), DensityUtils.dp2px(this, 10));
			if (i != 0)
			{
				params.leftMargin = DensityUtils.dp2px(this, 10);
			}

			mPointContainer.addView(point, params);
		}

		// 给viewpager设置数据
		mViewPager.setAdapter(new GuideAdapter());// list

		// 给viewpager设置滑动监听
		mViewPager.setOnPageChangeListener(new GuidePageListener());

		// 监听layout布局改变完成的监听
		mPointSelected.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout()
			{
				// 当布局完成时的回调
				mPointSpace = mPointContainer.getChildAt(1).getLeft() - mPointContainer.getChildAt(0).getLeft();
				Log.d(TAG, "mPointSpace : " + mPointSpace);

				// 移除监听
				mPointSelected.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				// mPointSelected.getViewTreeObserver().removeOnGlobalLayoutListener(this);
			}
		});
	}

	private class GuidePageListener implements OnPageChangeListener
	{

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
		{
			// 当页面滑动时的回调
			// positionOffset:滑动的百本比
			// positionOffsetPixels:滑动的像素

			// 获得两个点间的距离
			// int pointSpace = DensityUtils.dp2px(GuideUI.this, 10) * 2;

			// 动态点偏移的像素
			int offsetPx = (int) (mPointSpace * positionOffset + 0.5f) + position * mPointSpace;
			// 给动态的点设置marginLeft
			// android:layout_marginLeft=
			RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) mPointSelected.getLayoutParams();
			params.leftMargin = offsetPx;
			mPointSelected.setLayoutParams(params);
		}

		@Override
		public void onPageSelected(int position)
		{
			if (position == mDatas.size() - 1)
			{
				// 最后一个页面
				mBtnStart.setVisibility(View.VISIBLE);
			}
			else
			{
				mBtnStart.setVisibility(View.GONE);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state)
		{
		}

	}

	private class GuideAdapter extends PagerAdapter
	{

		@Override
		public int getCount()
		{
			if (mDatas != null) { return mDatas.size(); }
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{

			ImageView child = mDatas.get(position);

			container.addView(child);

			return child;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
		}

	}

	@Override
	public void onClick(View v)
	{
		if (v == mBtnStart)
		{
			clickStart();
		}
	}

	private void clickStart()
	{
		// 记录已经使用过引导页面
		PreferenceUtils.putBoolean(this, Constants.KEY_FIRST_USED, false);

		// 跳转
		Intent intent = new Intent(this, Main_FA.class);
		startActivity(intent);

		finish();
	}
}
