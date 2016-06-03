package com.bamboo.freechat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    @ViewInject(R.id.title)
    private ActTitle title;

    private String queryUser;
    private final String username = SPUtil.getDate("username");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title.setTitleName("查找好友");
    }


    private final Handler UserInfohandler = new Handler() {
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


    private final Handler addFriendHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Tag.SUCCESS) {
                String message = (String) msg.obj;
                Toast.makeText(ActSearch.this, message, Toast.LENGTH_SHORT).show();
            } else if (msg.what == Tag.FAILURE) {
                String message = (String) msg.obj;
                Toast.makeText(ActSearch.this, message, Toast.LENGTH_SHORT).show();
            } else if (msg.what == Tag.OTHER) {
                Toast.makeText(ActSearch.this, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query:
                queryUser = editText.getText().toString().trim();
                Dao.getUserInfo(username, queryUser, UserInfohandler);
                break;
            case R.id.add_friend:
                Dao.addFriend(username, queryUser, addFriendHandler);
                break;
        }
    }


}
