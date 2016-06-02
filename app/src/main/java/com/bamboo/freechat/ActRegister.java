package com.bamboo.freechat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bamboo.base.BaseActivity;
import com.bamboo.base.ContentView;
import com.bamboo.base.ViewInject;
import com.bamboo.common.Dao;
import com.bamboo.common.Tag;
import com.bamboo.dialog.DialogView;

/**
 * Created by bamboo on 16-5-30.
 */
@ContentView(R.layout.register)
public class ActRegister extends BaseActivity {

    @ViewInject(R.id.account)
    private EditText account;
    @ViewInject(R.id.passwd)
    private EditText passwd;
    @ViewInject(R.id.surePasswd)
    private EditText surePasswd;

    private String accountName;
    private String password;
    private String SurePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:

                register();
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Tag.SUCCESS) {

                Toast.makeText(ActRegister.this, "注册成功！",
                        Toast.LENGTH_LONG).show();

                DialogView.showDialog(ActRegister.this, "点击确认可进行登录", new DialogView.OnClickListener() {
                    @Override
                    public void onClick(DialogView dialogView) {
                        startActivity(new Intent(ActRegister.this, MainActivity.class));
                    }
                });

            } else if (msg.what == Tag.FAILURE) {
                Toast.makeText(ActRegister.this, "用户已存在",
                        Toast.LENGTH_LONG).show();
            } else if (msg.what == Tag.JUDGE) {
                Toast.makeText(ActRegister.this, "请检查网络",
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    public void register() {
        accountName = account.getText().toString().trim();
        password = passwd.getText().toString().trim();
        SurePassword = surePasswd.getText().toString().trim();
        if (password.isEmpty() || SurePassword.isEmpty() || accountName.isEmpty()) {
            Toast.makeText(this, "用户名或密码不能为空",
                    Toast.LENGTH_SHORT).show();
        } else {
            if (password.equals(SurePassword)) {
                Dao.userRegister(accountName, password, handler);
            } else {
                Toast.makeText(this, "两次密码不同,请重新输入！",
                        Toast.LENGTH_SHORT).show();
                passwd.setText("");
                surePasswd.setText("");
            }
        }
    }
}
