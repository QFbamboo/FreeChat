package com.bamboo.freechat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.bamboo.util.Toast;

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

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Tag.SUCCESS) {
                Toast.showShortToast("注册成功");
                startActivity(new Intent(ActRegister.this, MainActivity.class));
                finish();
            } else if (msg.what == Tag.FAILURE) {
                Toast.showShortToast("用户已存在");
            } else if (msg.what == Tag.OTHER) {
                Toast.showShortToast("请检查网络");
            }
        }
    };

    public void register() {
        accountName = account.getText().toString().trim();
        password = passwd.getText().toString().trim();
        SurePassword = surePasswd.getText().toString().trim();
        if (password.isEmpty() || SurePassword.isEmpty() || accountName.isEmpty()) {
            Toast.showShortToast("用户名或密码不能为空");
        } else if (!password.equals(SurePassword)) {
            Toast.showShortToast("两次密码不同,请重新输入！");
            passwd.setText("");
            surePasswd.setText("");
        } else {
            Dao.userRegister(accountName, password, handler);
        }
    }
}
