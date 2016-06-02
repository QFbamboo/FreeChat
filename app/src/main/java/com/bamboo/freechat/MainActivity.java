package com.bamboo.freechat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bamboo.base.BaseActivity;
import com.bamboo.base.ContentView;
import com.bamboo.base.ViewInject;
import com.bamboo.bean.User;
import com.bamboo.common.Dao;
import com.bamboo.common.Tag;
import com.bamboo.util.IMUtil;
import com.bamboo.util.SPUtil;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.account)
    private EditText account;
    @ViewInject(R.id.passwd)
    private EditText passwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断存入的用户ID信息是否为空，如果不是，则证明之登录，则直接显示聊天界面
        if (SPUtil.getDate("username") != "") {
            startActivity(new Intent(MainActivity.this, ActContent.class));
            finish();
        }
    }

    //接收用户登录返回的Handler
    private final Handler userInfoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Tag.SUCCESS) {
                User user = (User) msg.obj;
                SPUtil.setUser(user);//把用户信息保存到本地

                //登录成功之后跳转到聊天界面
                startActivity(new Intent(MainActivity.this, ActContent.class));
            } else if (msg.what == Tag.FAILURE) {
                Toast.makeText(MainActivity.this, "用户名或密码错误",
                        Toast.LENGTH_SHORT).show();
            }else if(msg.what==Tag.JUDGE){
                Toast.makeText(MainActivity.this, "请检查网络！",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                final String userId = account.getText().toString().trim();
                final String password = passwd.getText().toString().trim();
                if (userId.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    IMUtil.login(userId, password, new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            if (msg.what == Tag.SUCCESS) {
                                Dao.getUserInfo(userId, userId, userInfoHandler);
                            } else if (msg.what == Tag.FAILURE) {
                                Toast.makeText(MainActivity.this, "用户名或密码错误",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            case R.id.main_btnregister:
                startActivity(new Intent(this, ActRegister.class));
                break;
        }
    }
}