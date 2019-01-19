package com.gjs.gjsmall.home;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjs.gjsmall.R;

/**
 * 多个商品展示界面
 * @author
 */
@SuppressLint("SimpleDateFormat")
public class ExplainActivity
		extends Activity
		implements View.OnClickListener
{
	private ImageView iv_back;
	private ImageView explain;
	private Drawable  btnDrawable;
	private Resources resources;
	private TextView  exText;

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explain);
		iv_back = (ImageView) findViewById(R.id.iv_opinion_back);


		iv_back.setOnClickListener(this);


		explain = (ImageView) findViewById(R.id.explain_image);
		exText = (TextView) findViewById(R.id.explain_top_title);

		//initBundle();
		//新页面接收数据,选择页面
		Bundle bundle = this.getIntent().getExtras();
		//接收num和name值
		String num  = bundle.getString("num");
		String name = bundle.getString("name");
		if (name != null) {
			exText.setText(name);
		} else {
			exText.setText("逛集市");
		}

		if (num.equals("1")) {
			resources = getResources();
			btnDrawable = resources.getDrawable(R.drawable.explain4);
			explain.setBackground(btnDrawable);

		} else if (num.equals("2")) {
			resources = getResources();
			btnDrawable = resources.getDrawable(R.drawable.explain4);
			explain.setBackground(btnDrawable);
		} else if (num.equals("3")) {
			resources = getResources();
			btnDrawable = resources.getDrawable(R.drawable.explain4);
			explain.setBackground(btnDrawable);
		} else {
			resources = getResources();
			btnDrawable = resources.getDrawable(R.drawable.explain4);
			explain.setBackground(btnDrawable);
		}


	}

	//选择页面
	private void initBundle() {

	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_opinion_back:
				finish();
				break;
		}
	}
}
