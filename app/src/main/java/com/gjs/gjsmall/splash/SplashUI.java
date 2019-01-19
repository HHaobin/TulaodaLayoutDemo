package com.gjs.gjsmall.splash;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

import com.gjs.gjsmall.R;
import com.gjs.gjsmall.home.Main_FA;
import com.gjs.gjsmall.utils.Constants;
import com.gjs.gjsmall.utils.PreferenceUtils;


public class SplashUI extends Activity
{
	private final static long	DURATION	= 300;
	private View mRootView;
	private static final String TAG = "SplashUI";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_splash);

		
		mRootView = findViewById(R.id.splash_root);
		// 1.初始化view
		// 2.旋转动画
		//		RotateAnimation rotate = new RotateAnimation(0, 360,
		//														Animation.RELATIVE_TO_PARENT, 0.5f,
		//														Animation.RELATIVE_TO_PARENT, 0.5f);
		// 3.缩放动画
		ScaleAnimation scale = new ScaleAnimation(0, 1,
												  0, 1,
												  Animation.RELATIVE_TO_PARENT, 0.5f,
												  Animation.RELATIVE_TO_PARENT, 0.5f);

		//4.透明度动画
		AlphaAnimation alpha = new AlphaAnimation(0, 1);

		AnimationSet set = new AnimationSet(true);
		set.setDuration(DURATION);
		//		set.addAnimation(rotate);
		set.addAnimation(scale);
		set.addAnimation(alpha);

		//		set.setInterpolator(new BounceInterpolator());

		set.setAnimationListener(new SplashAnimationListener());

		mRootView.startAnimation(set);
	}

	private class SplashAnimationListener implements AnimationListener
	{

		@Override
		public void onAnimationStart(Animation animation)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd(Animation animation)
		{
			// 动画结束时页面跳转
			// 用户第一次使用时，进入引导界面
			boolean flag = PreferenceUtils.getBoolean(SplashUI.this, Constants.KEY_FIRST_USED, true);

			if (flag)
			{
				// 1.引导界面

				

				Intent intent = new Intent(SplashUI.this, GuideUI.class);
				startActivity(intent);
			}
			else
			{

				// 2.主界面

//				Intent intent4=new Intent(SplashUI.this,LauncherActivity.class);
//				startActivity(intent4);
				Intent intent = new Intent(SplashUI.this, Main_FA.class);
				startActivity(intent);
			}

			finish();

		}

		@Override
		public void onAnimationRepeat(Animation animation)
		{
			

		}

	}
}
