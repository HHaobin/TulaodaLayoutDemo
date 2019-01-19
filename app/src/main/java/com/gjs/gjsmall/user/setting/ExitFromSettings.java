package com.gjs.gjsmall.user.setting;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gjs.gjsmall.R;
import com.gjs.gjsmall.user.login.base.ActivityCollector;

public class ExitFromSettings extends Activity {
	//private MyDialog dialog;
	private LinearLayout layout;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit_dialog_from_settings);
		//dialog=new MyDialog(this);
		layout=(LinearLayout)findViewById(R.id.exit_layout2);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
							   Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}

	public void exitbutton1(View v) {
		//设置界面
//		Intent intent3 = new Intent(ExitFromSettings.this, SettingActivity.class);
//		startActivity(intent3);
		this.finish();
	}
	public void exitbutton0(View v) {
		Toast.makeText(ExitFromSettings.this, "成功退出登录", Toast.LENGTH_LONG).show();
		//获得实例对象
		sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		editor.putBoolean("ISCHECK", true);

		editor.putString("KEY", null);
		editor.putBoolean("LOGIN", false);

		editor.putString("IC_USERNAME", null);
		editor.putString("IC_AVATOR", null);
		editor.putString("IC_POINT", null);
		editor.putString("IC_PRODEPOIT", null);
		editor.commit();
		//设置界面
		Intent intent4 = new Intent(ExitFromSettings.this, SettingActivity.class);
		startActivity(intent4);
		ActivityCollector.finishAll();
		this.finish();
		//Main_FA.instance.finish();//关闭Main 这个Activity
	}

}
