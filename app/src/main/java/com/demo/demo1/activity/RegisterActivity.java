package com.demo.demo1.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.demo1.R;
import com.demo.demo1.utils.MD5utils;

public class RegisterActivity extends AppCompatActivity {
    private TextView tv_main_title;
    private Button btn_register;
    private TextView tv_back;
    private EditText et_username,et_psw,et_psw_again;
    private String username,psw,pswagin;
    private RelativeLayout title_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        inti();
    }
//初始化
    private void inti() {
        //从main_title_bar.xml
        tv_main_title=(TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        tv_back=(TextView) findViewById(R.id.tv_back);
        title_bar=(RelativeLayout) findViewById(R.id.title_bar);
        //从activity_register.xml
        btn_register=(Button)findViewById(R.id.btn_register);
        et_username=(EditText) findViewById(R.id.et_user_name);
        et_psw=(EditText)findViewById(R.id.et_psw);
        et_psw_again=(EditText)findViewById(R.id.et_psw_again);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }else  if (TextUtils.isEmpty(pswagin)){
                    Toast.makeText(RegisterActivity.this,"请再次输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }else  if (!psw.equals(pswagin)){
                    Toast.makeText(RegisterActivity.this,"确认密码错误",Toast.LENGTH_SHORT).show();
                    return;
                }else  if (isExistUsernName(username)){
                    Toast.makeText(RegisterActivity.this,"账户名称已存在",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    saveRegisterInfo(username,psw);
                    Intent data=new Intent();
                    data.putExtra("username",username);
                    setResult(RESULT_OK,data);
                    RegisterActivity.this.finish();
                }
            }
        });
    }
    private  void getEditString(){
        username=et_username.getText().toString().trim();
        psw=et_psw.getText().toString().trim();
        pswagin=et_psw_again.getText().toString().trim();
    }
    private boolean isExistUsernName(String username){
     boolean has_userName=false;
     SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
     String spPsw=sp.getString(username,"");
     if (!TextUtils.isEmpty(spPsw)){
         has_userName=true;
     }
     return  has_userName;
    }
    private void  saveRegisterInfo(String username,String psw){
        String md5psw= MD5utils.md5(psw);
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(username,md5psw);
        editor.commit();
    }
}
