package com.gjs.gjsmall.user;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjs.gjsmall.R;

/**
 * 意见反馈界面
 * @author http://yecaoly.taobao.com
 *
 */
public class User_opinion extends Activity
{

	private ImageView iv_back;
	private TextView  tv_goMyShop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.user_opinion);
		initView();
	}

	private void initView(){

//		iv_back=(ImageView) findViewById(R.id.iv_opinion_back);
//		tv_goMyShop=(TextView) findViewById(R.id.tv_goMyshop);
//
//		iv_back.setOnClickListener(this);
//		tv_goMyShop.setOnClickListener(this);

	}

//	@Override
//	public void onClick(View arg0) {
//		switch (arg0.getId()) {
//			case R.id.iv_opinion_back:
//				finish();
//				break;
//			case R.id.tv_goMyshop:
//				//进入恋上猫女衣坊的网店
//				Uri uri = Uri.parse("http://yecaoly.taobao.com");
//				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//				startActivity(intent);
//				break;
//			default:
//				break;
//		}
//	}



}
