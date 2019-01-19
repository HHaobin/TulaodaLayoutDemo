package com.gjs.gjsmall.user.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gjs.gjsmall.R;
import com.gjs.gjsmall.user.login.base.ActivityCollector;
import com.gjs.gjsmall.user.login.base.LoginBaseActivity;

public class IdentifycodeActivity extends LoginBaseActivity implements View.OnClickListener {

    private ImageView identify_setting_back;
    private Button    identify_button;
    private EditText  identifying_code;
    private String  string_identifying_code;
    private SharedPreferences sp;
    private Button submit;
    private TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identifycode);

        time = new TimeCount(60000, 1000);

        identify_setting_back = (ImageView) findViewById(R.id.identify_setting_back);
        identify_button= (Button) findViewById(R.id.identify_button);
        identifying_code = (EditText) findViewById(R.id.identifying_code);
        submit= (Button) findViewById(R.id.btn_login_submit);


        identify_setting_back.setOnClickListener(this);
        identifying_code.setOnClickListener(this);
        submit.setOnClickListener(this);

        ActivityCollector.addActivity(this);
    }

    /**
     *
     * 下一步 的 方法
     * @param v
     *
     *
     */
    public void identify(View v) {
        string_identifying_code =identifying_code.getText().toString().trim();


        if(TextUtils.isEmpty(string_identifying_code)){
            Toast.makeText(IdentifycodeActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
//        else if(!string_password_1_number.equals(string_password_2_number)){
//            Toast.makeText(IdentifycodeActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
//            return;
//        }
        else {
//            //获得实例对象
//            sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putString("STRING_IDENTIFYING_CODE", string_identifying_code);
//            editor.commit();

            //密码界面
            Intent intent1=new Intent(this, RegistersuccessActivity.class);
            startActivity(intent1);


        }
    }


    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.identify_setting_back:
                    finish();
                    break;
                case R.id.btn_login_submit:
                    time.start();// 开始计时
                    break;
                default:
                    break;
            }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            submit.setText("获取验证码");
            submit.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            submit.setClickable(false);//防止重复点击
            submit.setText(millisUntilFinished / 1000 + "秒后重发");
        }
    }
}
