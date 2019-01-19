package com.gjs.gjsmall.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjs.gjsmall.R;
import com.gjs.gjsmall.home.Main_FA;
import com.gjs.gjsmall.user.login.base.ActivityCollector;
import com.gjs.gjsmall.user.login.base.LoginBaseActivity;

public class RegistersuccessActivity extends LoginBaseActivity
        implements View.OnClickListener{
    private ImageView registersuccess_setting_back;
    private TextView success_to_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registersuccess);

        registersuccess_setting_back = (ImageView) findViewById(R.id.registersuccess_setting_back);
        success_to_login = (TextView) findViewById(R.id.success_to_login);

        registersuccess_setting_back.setOnClickListener(this);
        success_to_login.setOnClickListener(this);

        ActivityCollector.addActivity(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registersuccess_setting_back:
                //主界面
                Intent intent1=new Intent(this, Main_FA.class);
                startActivity(intent1);
                ActivityCollector.finishAll();
                break;
            case R.id.success_to_login:
                ActivityCollector.finishAll();
                //密码界面
                Intent intent2=new Intent(this, LoginActivity.class);
                startActivity(intent2);
                break;
            default:
        }
    }
}
