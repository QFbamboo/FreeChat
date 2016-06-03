package com.bamboo.freechat;

import android.os.Bundle;
import android.widget.TextView;

import com.bamboo.base.BaseActivity;
import com.bamboo.base.ContentView;
import com.bamboo.base.ViewInject;
import com.bamboo.common.Message;

/**
 * Created by bamboo on 16-6-3.
 */
@ContentView(R.layout.msg_content)
public class ActMessage extends BaseActivity {

    @ViewInject(R.id.msgTime)
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String date = new Message().getFromUserName();

        textView.setText(date);

    }
}
