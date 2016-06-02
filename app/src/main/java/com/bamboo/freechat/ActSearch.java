package com.bamboo.freechat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bamboo.base.BaseActivity;
import com.bamboo.base.ContentView;
import com.bamboo.base.LoadPictrue;
import com.bamboo.base.ViewInject;
import com.bamboo.bean.User;
import com.bamboo.common.Dao;
import com.bamboo.common.Tag;
import com.bamboo.util.SPUtil;

/**
 * Created by bamboo on 16-6-2.
 */
@ContentView(R.layout.search)
public class ActSearch extends BaseActivity {

    @ViewInject(R.id.editText)
    private EditText editText;
    @ViewInject(R.id.search_avatar)
    private ImageView imageView;
    @ViewInject(R.id.search_user)
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Tag.SUCCESS) {
                User user = (User) msg.obj;
                String avatar = user.getAvatar();
                String username = user.getUsername();
                new LoadPictrue(ActSearch.this, avatar, imageView);//显示头像
                textView.setText(username);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query:
                String queryUser = editText.getText().toString().trim();
                String username = SPUtil.getDate("username");
                Dao.getUserInfo(username, queryUser, handler);
                break;
        }
    }

}
