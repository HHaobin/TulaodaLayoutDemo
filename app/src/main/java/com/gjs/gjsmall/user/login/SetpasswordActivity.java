package com.gjs.gjsmall.user.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gjs.gjsmall.R;
import com.gjs.gjsmall.user.login.base.ActivityCollector;
import com.gjs.gjsmall.user.login.base.LoginBaseActivity;

public class SetpasswordActivity extends LoginBaseActivity
        implements View.OnClickListener {
    private ImageView setpassword_setting_back;
    private Button    register_button;
    private EditText  password_1_number,password_2_number;
    private String  string_password_1_number,string_password_2_number;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpassword);
        setpassword_setting_back = (ImageView) findViewById(R.id.setpassword_setting_back);
        register_button= (Button) findViewById(R.id.register_next_button);
        password_1_number = (EditText) findViewById(R.id.password_1_number);
        password_2_number = (EditText) findViewById(R.id.password_2_number);

        setpassword_setting_back.setOnClickListener(this);
        password_1_number.setOnClickListener(this);
        password_2_number.setOnClickListener(this);

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
        string_password_1_number =password_1_number.getText().toString().trim();
        string_password_2_number =password_2_number.getText().toString().trim();

        if(TextUtils.isEmpty(string_password_1_number)||TextUtils.isEmpty(string_password_2_number)){
            Toast.makeText(SetpasswordActivity.this, "密码或确认密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!string_password_1_number.equals(string_password_2_number)){
            Toast.makeText(SetpasswordActivity.this, "密码和确认密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            //获得实例对象
            sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("STRING_PASSWORD_1_NUMBER", string_password_1_number);
            editor.putString("STRING_PASSWORD_2_NUMBER", string_password_2_number);
            editor.commit();

            //界面
            Intent intent1=new Intent(this, IdentifycodeActivity.class);
            startActivity(intent1);


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setpassword_setting_back:
                finish();
                break;

            default:
        }
    }


    
}
