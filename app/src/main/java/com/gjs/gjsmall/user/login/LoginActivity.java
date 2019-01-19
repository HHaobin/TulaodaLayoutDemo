package com.gjs.gjsmall.user.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjs.gjsmall.R;
import com.gjs.gjsmall.home.Main_FA;
import com.gjs.gjsmall.user.login.base.ActivityCollector;
import com.gjs.gjsmall.user.login.base.LoginBaseActivity;
import com.gjs.gjsmall.utils.StreamTool;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends LoginBaseActivity
		implements OnClickListener{
//	protected static final int SUCCESS = 1;
//	protected static final int ERROR = 2;
//	EditText et_qq ,et_pwd;
//	TextView tv_result;

	protected static final int SUCCESS = 0;
	protected static final int ERROR = 1;
	private EditText ed_number;
	private EditText ed_password;
	private TextView tv_result;
	private TextView username,point,prodepoit,available_rc_balance;
	private TextView login_to_register,login_to_password;
	private ImageView avator;
	private ImageView login_setting_back;
	private String    key;
	private String    path;
	private String    number;
	private String    password;
	private String    ic_username;

	private Bitmap    bitmap;
	private Bitmap    bmImg;
	private ImageView imView,qq;
	private String imageUrl = "http://content.52pk.com/files/100623/2230_102437_1_lit.jpg";
	private URL myFileUrl;

	private SharedPreferences sp;
	private CheckBox          rem_pw, auto_login;
	private String userNameValue,passwordValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


		//初始化 控件
		login_setting_back = (ImageView) findViewById(R.id.login_setting_back);
		login_to_register = (TextView) findViewById(R.id.login_to_register);
		login_to_password = (TextView) findViewById(R.id.login_to_password);
		ed_number = (EditText) findViewById(R.id.ed_number);//帐号
		ed_password = (EditText) findViewById(R.id.ed_password);//密码
		tv_result = (TextView) findViewById(R.id.tv_result);//结果
		username= (TextView) findViewById(R.id.user_name);//用户名
//		point = (TextView) findViewById(R.id.point);
//		prodepoit = (TextView) findViewById(R.id.prodepoit);
//		available_rc_balance = (TextView) findViewById(R.id.available_rc_balance);
//		avator = (ImageView)findViewById(R.id.avator);
//		qq = (ImageView)findViewById(R.id.qq);
		//      this.imView = ((ImageView)findViewById(R.id.imageView));
		//	    DownloadImageTask localDownloadImageTask = new DownloadImageTask();
		//	    String[] arrayOfString = new String[1];
		//	    arrayOfString[0] = this.imageUrl;
		//	    localDownloadImageTask.execute(arrayOfString);
		rem_pw = (CheckBox) findViewById(R.id.cb_mima);
		auto_login = (CheckBox) findViewById(R.id.cb_auto);

		rem_pw.setChecked(true);
		auto_login.setChecked(false);
		login_to_register.setOnClickListener(this);
		login_to_password.setOnClickListener(this);
		login_setting_back.setOnClickListener(this);
		//获得实例对象
		sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);



//		//判断记住密码多选框的状态
//		if(sp.getBoolean("ISCHECK", false))
//		{
//			//设置默认是记录密码状态
//			rem_pw.setChecked(true);
//			Editor editor=sp.edit();
//			editor.putString("USER_NAME", "");
//			editor.putString("PASSWORD", "");
//
//			editor.putString("IC_USERNAME","1");
//			editor.putString("IC_AVATOR","1");
//			editor.putString("IC_POINT","1");
//			editor.putString("IC_PRODEPOIT","1");
//
//			//找回记住的帐号密码
//			ed_number.setText(sp.getString("USER_NAME", ""));
//			ed_password.setText(String.valueOf(Integer.parseInt(sp.getString("PASSWORD", ""))/8));
//
//			login_setting_back.setOnClickListener(this);
//			//判断自动登陆多选框状态
//			if(sp.getBoolean("AUTO_ISCHECK", false))
//			{
//				//设置默认是自动登录状态
//				auto_login.setChecked(true);
//				login(getCurrentFocus());
//				//跳转界面
//				//  				Intent intent = new Intent(this,LogoActivity.class);
//				//  				this.startActivity(intent);
//
//			}
//		}

		//监听自动登录多选框事件
		auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
//				if (auto_login.isChecked()) {
//					System.out.println("自动登录已选中");
//					sp.edit().putBoolean("AUTO_ISCHECK", true).commit();
//
//				} else {
//					System.out.println("自动登录没有选中");
//					sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
//				}
			}
		});

		//监听记住密码多选框按钮事件
		rem_pw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
//				if (rem_pw.isChecked()) {
//
//					System.out.println("记住密码已选中");
//					Toast.makeText(LoginActivity.this, "记住密码已选中", Toast.LENGTH_SHORT).show();
//					sp.edit().putBoolean("ISCHECK", true).commit();
//
//				}else {
//
//					System.out.println("记住密码没有选中");
//					Toast.makeText(LoginActivity.this, "记住密码已选中", Toast.LENGTH_SHORT).show();
//					sp.edit().putBoolean("ISCHECK", false).commit();
//
//				}

			}
		});


		ActivityCollector.addActivity(this);
    }



	/**
	 *
	 * handler
	 */
	Handler handler = new Handler(){

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
				case SUCCESS:
					String value = (String) msg.obj;
					//测试结果
					//tv_result.setText(value);
					//跳转界面
					//解析最终响应数据
					loginresult(value);

					break;
				case ERROR:

					//弹 土司
					Toast.makeText(LoginActivity.this, "对不起, 俺出错了. ", Toast.LENGTH_SHORT).show();
					break;

				default:
					break;
			}
		};
	};



	/**
	 *
	 * 登录的 方法
	 * @param v
	 *
	 *
	 */
	public void login(View v){

		//path = getResources().getString(R.string.ip);
		path="http://gjs.wsh68.com/mobile/index.php?act=login";
		number = ed_number.getText().toString().trim();
		password = ed_password.getText().toString().trim();

		//		判断 是否为空
		if(TextUtils.isEmpty(number)||TextUtils.isEmpty(password)){
			Toast.makeText(LoginActivity.this, "号码或密码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

//		//存储帐号密码
//		if(rem_pw.isChecked()){
//			SharedPreferences.Editor editor =sp.edit();
//			editor.putString("USER_NAME", number);
//			editor.putString("PASSWORD", password);
//			editor.putBoolean("ISCHECK",true);
//
//			editor.commit();
//		}else{
//			Editor editor=sp.edit();
//			editor.putString("USER_NAME", null);
//			editor.putString("PASSWORD", null);
//			editor.putBoolean("ISCHECK",false);
//
//			editor.commit();
//		}

		//发请求
		new Thread(){
			public void run() {

				//  http://188.188.4.100:8080/day06_android/login?number=110&password=123
				try {

					HttpClient client = new DefaultHttpClient();

					// http://188.188.4.100:8080/day06_android/login
					HttpPost post = new HttpPost(path);

					//两个键值对
					NameValuePair pair1=new BasicNameValuePair("username", number);//number  "H_Haobin"
					NameValuePair pair2=new BasicNameValuePair("password", password);//password  "12345678"
					NameValuePair pair3=new BasicNameValuePair("client", "wap");

					//把两个键值对 放到 list 中去, 然后再 把 list 放入到 实体中
					List<NameValuePair> list = new ArrayList<NameValuePair>();

					list.add(pair1);
					list.add(pair2);
					list.add(pair3);

					//这里传 了 UTF-8 就可以将数据 编码后带过去 了
					post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));

					HttpResponse response = client.execute(post);

					//获得状态行对象,然后再 获得状态行中的状态码
					int code = response.getStatusLine().getStatusCode();

					if(code==200){

						//获得响应体,  获得响应体中的 流的数据
						//接下来的代码跟之前一样的
						InputStream in = response.getEntity().getContent();

						String value = StreamTool.decodeStream(in);

						loginlater(value);

						//						//发消息
						//						Message msg = Message.obtain();
						//						msg.what = SUCCESS;
						//						msg.obj = value;
						//						handler.sendMessage(msg);

						//						//登录成功和记住密码框为选中状态才保存用户信息
						//						if(rem_pw.isChecked())
						//						{
						//						 //记住用户名、密码、
						//						  Editor editor = sp.edit();
						//						  editor.putBoolean("ISCHECK", true);
						//						  editor.putString("USER_NAME", userNameValue);
						//						  editor.putString("PASSWORD",passwordValue);
						//						  editor.commit();
						//						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					Message msg = Message.obtain();
					msg.what = ERROR;
					handler.sendMessage(msg);
				}


			};

		}.start();


	}
	//	JSONObject jsonObject = new JSONObject(json);
	//	String name = jsonObject.getString("name");
	//	int age = jsonObject.getInt("age");
	//	boolean male = jsonObject.getBoolean("male");
	//	JSONObject addressJSON = jsonObject.getJSONObject("address");
	//	String street = addressJSON.getString("street");
	//	String city = addressJSON.getString("city");
	//	String country = addressJSON.getString("country");
	//	Address address = new Address(street, city, country);
	//	Person person = new Person(name, age, male, address);
	//	System.out.println(person);

	/**
	 * 登录之后的获取 方法
	 * @param value
	 *
	 *
	 */
	public void loginlater(String value){

		//path = getResources().getString(R.string.ip);
		path="http://gjs.wsh68.com/mobile/index.php?act=member_index";

		try {
			JSONTokener jsonParser = new JSONTokener(value);
			// 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
			// 如果此时的读取位置在"name" : 了，那么nextValue就是"yuanzhifei89"（String）
			JSONObject person = (JSONObject) jsonParser.nextValue();
			// 接下来的就是JSON对象的操作了
			//person.getJSONArray("phone");
			person.getString("code");
			//person.getInt("age");
			JSONObject datas=person.getJSONObject("datas");
			//person.getBoolean("married");
			datas.getString("username");
			key=datas.getString("key");


		} catch (JSONException ex) {
			// 异常处理代码
		}
		//发请求
		new Thread(){
			public void run() {

				//  http://188.188.4.100:8080/day06_android/login?number=110&password=123
				try {

					HttpClient client = new DefaultHttpClient();

					// http://188.188.4.100:8080/day06_android/login
					HttpPost post = new HttpPost(path);

					//两个键值对
					NameValuePair pair1 =new BasicNameValuePair("key", key);


					//把两个键值对 放到 list 中去, 然后再 把 list 放入到 实体中
					List<NameValuePair> list = new ArrayList<NameValuePair>();

					list.add(pair1);

					//这里传 了 UTF-8 就可以将数据 编码后带过去 了
					post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));

					HttpResponse response = client.execute(post);

					//获得状态行对象,然后再 获得状态行中的状态码
					int code = response.getStatusLine().getStatusCode();

					if(code==200){

						//获得响应体,  获得响应体中的 流的数据
						//接下来的代码跟之前一样的
						InputStream in = response.getEntity().getContent();

						String value = StreamTool.decodeStream(in);



						//发消息
						Message msg = Message.obtain();
						msg.what = SUCCESS;
						msg.obj = value;
						handler.sendMessage(msg);



					}

				} catch (Exception e) {
					e.printStackTrace();
					Message msg = Message.obtain();
					msg.what = ERROR;
					handler.sendMessage(msg);
				}


			};

		}.start();


	}

	/**
	 * 登录之后的获取 方法
	 * @param value
	 *
	 *
	 */
	public void loginresult(String value){


		try {
			//username.setText("sssasasas");
			JSONTokener jsonParser = new JSONTokener(value);
			username.setText("sssasaspppppsas1");
			// 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
			// 如果此时的读取位置在"name" : 了，那么nextValue就是"yuanzhifei89"（String）
			JSONObject person = (JSONObject) jsonParser.nextValue();

			// 接下来的就是JSON对象的操作了
			//person.getJSONArray("phone");
			//person.getString("code");
			//person.getInt("age");
			String code=person.getString("code");
			JSONObject datas=person.getJSONObject("datas");
			//person.getBoolean("married");

				JSONObject member_info = datas.getJSONObject("member_info");
				//String ic_available_rc_balance = member_info.getString("available_rc_balance");
				ic_username = member_info.getString("user_name");//用户名
				String ic_avator    = member_info.getString("avator");//头像
				String ic_point     = member_info.getString("point");//积分
				String ic_prodepoit = member_info.getString("predepoit");//余额
				//			username.setText(ic_username);
				//			point.setText(ic_point);
				//			prodepoit.setText(ic_prodepoit);
				//available_rc_balance.setText(ic_available_rc_balance);
				//			username.setText(ic_username);
				//			point.setText(ic_point);
				//			prodepoit.setText(ic_prodepoit);
				//available_rc_balance.setText(ic_available_rc_balance);


				//			DownloadImageTask localDownloadImageTask = new DownloadImageTask(avator);
				//			String[] arrayOfString = new String[1];
				//			arrayOfString[0] = ic_avator;
				//			localDownloadImageTask.execute(arrayOfString);

			//Toast.makeText(LoginActivity.this, ic_avator, Toast.LENGTH_LONG).show();Toast.makeText(LoginActivity.this, ic_avator, Toast.LENGTH_LONG).show();
				SharedPreferences.Editor editor = sp.edit();
//				editor.putString("USER_NAME", number);
//				editor.putString("PASSWORD", String.valueOf((Integer.parseInt(password) * 8)));
				editor.putBoolean("ISCHECK", true);

				editor.putString("KEY", key);
				editor.putBoolean("LOGIN", true);

				editor.putString("IC_USERNAME", ic_username);
				editor.putString("IC_AVATOR", ic_avator);
				editor.putString("IC_POINT", ic_point);
				editor.putString("IC_PRODEPOIT", ic_prodepoit);
				editor.commit();
				Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
				//延时代码
				new Handler().postDelayed(new Runnable(){

					@Override
					public void run() {
						//主界面
						Intent intent3 = new Intent(LoginActivity.this, Main_FA.class);
						startActivity(intent3);
						ActivityCollector.finishAll();
					}

				 },2000);


				//测试用，可删
				//username.setText(sp.getString("IC_USERNAME", ""));
				//tv_result.setText(value);



		}catch (Exception e) {
			// 异常处理代码

			try {

				JSONTokener jsonParser = new JSONTokener(value);

				// 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
				// 如果此时的读取位置在"name" : 了，那么nextValue就是"yuanzhifei89"（String）
				JSONObject person = null;
				person = (JSONObject) jsonParser.nextValue();
				// 接下来的就是JSON对象的操作了
				//person.getJSONArray("phone");
				//person.getString("code");
				//person.getInt("age");
				String code=person.getString("code");
				String login=person.getString("login");
				JSONObject datas=person.getJSONObject("datas");
				//person.getBoolean("married");
				String ic_error = datas.getString("error");
				Toast.makeText(LoginActivity.this, ic_error, Toast.LENGTH_LONG).show();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

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


	public Bitmap returnBitMap(String paramString)
	{
		return this.bitmap;
	}



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

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.login_setting_back:
				finish();
				break;
			case R.id.login_to_register:
				//注册界面
				Intent intent1=new Intent(this, RegisterActivity.class);
				startActivity(intent1);
				break;

			case R.id.login_to_password:
				//忘记密码界面
				Intent intent2=new Intent(this, SetpasswordActivity.class);
				startActivity(intent2);
				break;
			default:
		}
	}
}
