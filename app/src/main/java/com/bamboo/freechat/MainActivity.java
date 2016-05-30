package com.bamboo.freechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bamboo.base.BaseActivity;
import com.bamboo.base.ContentView;
import com.bamboo.base.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.account)
    private EditText account;
    @ViewInject(R.id.passwd)
    private EditText passwd;
    @ViewInject(R.id.login)
    private Button login;
    @ViewInject(R.id.register)
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:

                break;
            case  R.id.register:
                startActivity(new Intent(MainActivity.this,ActRegister.class));
                break;
        }
    }
}