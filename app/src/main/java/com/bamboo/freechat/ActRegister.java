package com.bamboo.freechat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.bamboo.base.BaseActivity;
import com.bamboo.base.ContentView;
import com.bamboo.base.ViewInject;

/**
 * Created by bamboo on 16-5-30.
 */
@ContentView(R.layout.register)
public class ActRegister extends BaseActivity{

    @ViewInject(R.id.account)
    private EditText account;
    @ViewInject(R.id.passwd)
    private EditText passwd;
    @ViewInject(R.id.surePasswd)
    private EditText surePasswd;
    @ViewInject(R.id.register)
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String accountName=account.getText().toString().trim();
        String password=passwd.getText().toString().trim();
        String SurePassword=surePasswd.getText().toString().trim();
        if (password!=SurePassword){

        }
    }
}
