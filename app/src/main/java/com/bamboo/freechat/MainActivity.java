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

//        DialogView.showDialog(this, " ", new DialogView.OnClickListener() {
//            @Override
//            public void onClick(DialogView dialogView) {
//
//            }
//        });
        if (!SPUtil.getDate("userid").isEmpty()) {
            startActivity(new Intent(MainActivity.this, ActContent.class));
            finish();
        }
    }

    Handler userInfoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Tag.SUCCESS) {

                startActivity(new Intent(MainActivity.this, ActContent.class));
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
                }
                IMUtil.login(userId, password, new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == 1) {
                            Dao.getUserInfo(userId, userId, userInfoHandler);
//                            startActivity(new Intent(MainActivity.this, ActContent.class));
                        } else if (msg.what == 0) {
                            Toast.makeText(MainActivity.this, "用户名或密码错误",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.main_btnregister:
                startActivity(new Intent(this, ActRegister.class));
                break;
        }
    }
}