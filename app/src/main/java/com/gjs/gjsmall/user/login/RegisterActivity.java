package com.gjs.gjsmall.user.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjs.gjsmall.R;
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
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends LoginBaseActivity implements View.OnClickListener {

    protected static final int SUCCESS = 0;
    protected static final int ERROR = 1;

    private TextView  re_result;
    private ImageView register_setting_back;
    private Button    register_button;
    private EditText  register_name,register_phone_number,register_email_number;
    private String  string_register_name,string_phone_number,string_email_number;
    private String  string_password_1_number,string_password_2_number;
    private String path;
    private EditText  password_1_number,password_2_number;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        re_result = (TextView) findViewById(R.id.re_result);//结果
        register_setting_back = (ImageView) findViewById(R.id.register_setting_back);//返回按钮
        register_button= (Button) findViewById(R.id.register_next_button);//下一步
        register_name =  (EditText) findViewById(R.id.register_name);//用户名
        register_email_number = (EditText) findViewById(R.id.register_email_number);//邮箱
        register_phone_number = (EditText) findViewById(R.id.register_phone_number);//手机
        password_1_number = (EditText) findViewById(R.id.password_1_number);//密码
        password_2_number = (EditText) findViewById(R.id.password_2_number);//确认密码

        register_name.setOnClickListener(this);
        password_1_number.setOnClickListener(this);
        password_2_number.setOnClickListener(this);
        register_setting_back.setOnClickListener(this);
        register_email_number.setOnClickListener(this);
        register_phone_number.setOnClickListener(this);

        ActivityCollector.addActivity(this);
    }

    /**
     *
     * 下一步 的 方法
     * @param v
     *
     *
     */
    public void renext(View v) {

        string_register_name= register_name.getText().toString().trim();
        string_email_number =register_email_number.getText().toString().trim();
        string_phone_number=register_phone_number.getText().toString().trim();
        string_password_1_number =password_1_number.getText().toString().trim();
        string_password_2_number =password_2_number.getText().toString().trim();

        if(TextUtils.isEmpty(string_register_name)){
            Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(string_email_number)||TextUtils.isEmpty(string_phone_number)){
            Toast.makeText(RegisterActivity.this, "邮箱或手机不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(string_password_1_number)||TextUtils.isEmpty(string_password_2_number)){
            Toast.makeText(RegisterActivity.this, "密码或确认密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!string_password_1_number.equals(string_password_2_number)){
            Toast.makeText(RegisterActivity.this, "密码和确认密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            //获得实例对象
            sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("STRING_REGISTER_NAME", string_register_name);
            editor.putString("STRING_EMAIL_NUMBER", string_email_number);
            editor.putString("STRING_PHONE_NUMBER", string_phone_number);
            editor.putString("STRING_PASSWORD_1_NUMBER", string_password_1_number);
            editor.putString("STRING_PASSWORD_2_NUMBER", string_password_2_number);
            editor.commit();

//            //密码界面
//            Intent intent1=new Intent(this, SetpasswordActivity.class);
//            startActivity(intent1);

            Register();
        }



    }

    private void Register() {
        path="http://gjs.wsh68.com/mobile/index.php?act=login&op=register";
        //发请求
        new Thread(){
            public void run() {

                try {

                    HttpClient client = new DefaultHttpClient();

                    HttpPost post = new HttpPost(path);

                    //两个键值对
                    NameValuePair pair1 =new BasicNameValuePair("username", string_register_name);//number
                    NameValuePair pair2 =new BasicNameValuePair("password", string_password_1_number);//password
                    NameValuePair pair3 =new BasicNameValuePair("password_confirm", string_password_2_number);//password
                    NameValuePair pair4 =new BasicNameValuePair("email", string_email_number);
                    NameValuePair pair5 =new BasicNameValuePair("client", "wap");

                    //把两个键值对 放到 list 中去, 然后再 把 list 放入到 实体中
                    List<NameValuePair> list = new ArrayList<NameValuePair>();

                    list.add(pair1);
                    list.add(pair2);
                    list.add(pair3);
                    list.add(pair4);
                    list.add(pair5);

                    //这里传 了 UTF-8 就可以将数据 编码后带过去 了
                    post.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));

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
     *
     * handler
     */
    Handler handler = new Handler(){

        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case SUCCESS:
                    String value = (String) msg.obj;
                    //tv_result.setText(value);
                    //跳转界面
                    //解析最终响应数据
                    registerResult(value);

                    break;
                case ERROR:

                    //弹 土司
                    Toast.makeText(RegisterActivity.this, "对不起, 俺出错了. ", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        };
    };

    /**
     * 登录之后的获取 方法
     * @param value
     *
     *
     */
    public void registerResult(String value){


        try {
            JSONTokener jsonParser = new JSONTokener(value);
            // 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
            // 如果此时的读取位置在"name" : 了，那么nextValue就是"yuanzhifei89"（String）
            JSONObject person = (JSONObject) jsonParser.nextValue();

            // 接下来的就是JSON对象的操作了
            //person.getJSONArray("phone");
            //person.getString("code");
            //person.getInt("age");
            String code=person.getString("code");
            JSONObject datas=person.getJSONObject("datas");

                String ic_key      = datas.getString("key");//key
                String re_ic_username = datas.getString("username");//用户名

                SharedPreferences.Editor editor =sp.edit();
                editor.putString("IC_KEY", ic_key);
                editor.putString("RE_IC_USERNAME",re_ic_username);
                editor.commit();

                re_result.setText(value+sp.getString("IC_KEY", ""));
                //注册成功界面
                Intent intent1 =new Intent(this, RegistersuccessActivity.class);
                startActivity(intent1);


            //测试用，可删
//            username.setText(sp.getString("IC_KEY", ""));
            //re_result.setText(value);

        }catch (Exception e) {
            // 异常处理代码
            try {
                JSONTokener jsonParser = new JSONTokener(value);
                // 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
                // 如果此时的读取位置在"name" : 了，那么nextValue就是"yuanzhifei89"（String）
                JSONObject person = (JSONObject) jsonParser.nextValue();

                // 接下来的就是JSON对象的操作了
                //person.getJSONArray("phone");
                //person.getString("code");
                //person.getInt("age");
                String code=person.getString("code");
                JSONObject datas=person.getJSONObject("datas");

                String ic_error     = datas.getString("error");//error

                SharedPreferences.Editor editor =sp.edit();
                editor.putString("IC_ERROR", ic_error);
                editor.commit();
                Toast.makeText(RegisterActivity.this, ic_error, Toast.LENGTH_SHORT).show();
                re_result.setText(value+sp.getString("IC_ERROR", ""));

                //测试用，可删
                //            username.setText(sp.getString("IC_KEY", ""));
                //re_result.setText(value);

            }catch (Exception e1) {
                // 异常处理代码
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_setting_back:
                finish();
                break;

            default:
        }
    }
}
