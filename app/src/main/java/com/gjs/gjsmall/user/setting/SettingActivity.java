package com.gjs.gjsmall.user.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.gjs.gjsmall.R;
import com.gjs.gjsmall.home.Main_FA;
import com.gjs.gjsmall.user.login.LoginActivity;
import com.gjs.gjsmall.user.login.base.ActivityCollector;
import com.gjs.gjsmall.utils.button.CheckSwitchButton;

public class SettingActivity extends Activity implements View.OnClickListener {
	CheckSwitchButton mCheckSwitchButton1,mCheckSwitchButton2,mCheckSwitchButton3;
	ImageView setting_back;
	Button exit_settings;
	private SharedPreferences sp;
	Boolean login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		setting_back= (ImageView) findViewById(R.id.setting_back);
		exit_settings=(Button) findViewById(R.id.exit_settings);
		setting_back.setOnClickListener(this);

		//获得实例对象
		sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		login =sp.getBoolean("LOGIN",false);
		if(login){
			exit_settings.setText("退出登录");
		}else{
			exit_settings.setText("登录/注册");
		}

		initCheck();
	}

	private void initCheck() {
		mCheckSwitchButton1 = (CheckSwitchButton) findViewById(R.id.check_button_3);
		mCheckSwitchButton2 = (CheckSwitchButton) findViewById(R.id.check_button_3);
		mCheckSwitchButton3 = (CheckSwitchButton) findViewById(R.id.check_button_3);
		mCheckSwitchButton3.setChecked(true);
		mCheckSwitchButton1.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				if(b){
					//选中
					Toast.makeText(getApplicationContext(), "1选中", Toast.LENGTH_SHORT).show();

				}else{
					//未选中
					Toast.makeText(getApplicationContext(), "1未选中", Toast.LENGTH_SHORT).show();
				}
			}
		});
		mCheckSwitchButton2.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				if(b){
					//选中
					Toast.makeText(getApplicationContext(), "2选中", Toast.LENGTH_SHORT).show();

				}else{
					//未选中
					Toast.makeText(getApplicationContext(), "2未选中", Toast.LENGTH_SHORT).show();
				}
			}
		});
		mCheckSwitchButton3.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				if(b){
					//选中
					Toast.makeText(getApplicationContext(), "3选中", Toast.LENGTH_SHORT).show();

				}else{
					//未选中
					Toast.makeText(getApplicationContext(), "3未选中", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.setting_back:
				//主界面
				Intent intent3 = new Intent(SettingActivity.this, Main_FA.class);
				startActivity(intent3);
				finish();
				break;
		}
	}

	public void exit_settings(View v) {
		if(login){
			//退出  伪“对话框”，其实是一个activity
			Intent intent1 = new Intent(this, ExitFromSettings.class);
			startActivity(intent1);
			ActivityCollector.addActivity(this);
		}else{
			//进入登录界面
			Intent intent2 = new Intent(this, LoginActivity.class);
			startActivity(intent2);
			ActivityCollector.addActivity(this);
		}
	}

	//改写物理按键——返回的逻辑
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode== KeyEvent.KEYCODE_BACK)
		{
			//主界面
			Intent intent3 = new Intent(SettingActivity.this, Main_FA.class);
			startActivity(intent3);
			finish();
		}

		return super.onKeyDown(keyCode, event);
	}

}
