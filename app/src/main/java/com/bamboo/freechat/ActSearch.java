package com.bamboo.freechat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bamboo.bean.Friend;
import com.bamboo.util.Toast;
import com.bamboo.base.BaseActivity;
import com.bamboo.base.ContentView;
import com.bamboo.base.ViewInject;
import com.bamboo.bean.User;
import com.bamboo.common.Dao;
import com.bamboo.common.Tag;
import com.bamboo.util.ImgHelper;
import com.bamboo.util.SPUtil;
import com.bamboo.view.AvatorView;
import com.bamboo.view.TitleView;

import java.util.Iterator;
import java.util.List;

/**
 * Created by bamboo on 16-6-2.
 */
@ContentView(R.layout.search)
public class ActSearch extends BaseActivity {

    @ViewInject(R.id.editText)
    private EditText editText;
    @ViewInject(R.id.search_avatar)
    private AvatorView imageView;
    @ViewInject(R.id.search_user)
    private TextView textView;
    @ViewInject(R.id.add_friend)
    private TextView addFrinend;
    @ViewInject(R.id.title)
    private TitleView title;
    private String queryUser;
    private Friend friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title.setTitleName("查找好友");
        getFriendInfo();
    }


    private final Handler UserInfohandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Tag.SUCCESS) {
                User user = (User) msg.obj;
                ImgHelper.setImage(imageView, user.getAvatar());
                textView.setText(user.getUsername());

                if (queryUser.equals(SPUtil.getData("username"))) {
                    addFrinend.setTextColor(0xccaaaaaa);
                    addFrinend.setBackgroundColor(0x00000000);
                    addFrinend.setText("当前用户");
                    addFrinend.setClickable(false);
                } else {
                    if (friend != null) {
                        if (queryUser.equals(friend.getUsername())) {
                            addFrinend.setText("已添加");
                            addFrinend.setTextColor(0xccaaaaaa);
                            addFrinend.setBackgroundColor(0x00000000);
                            addFrinend.setClickable(false);
                        } else {
                            addFrinend.setText("添加");
                            addFrinend.setBackgroundColor(0xff669999);
                            addFrinend.setTextColor(0xffffffff);
                            addFrinend.setClickable(true);
                        }
                    } else {
                        addFrinend.setText("添加");
                        addFrinend.setBackgroundColor(0xff669999);
                        addFrinend.setTextColor(0xffffffff);
                        addFrinend.setClickable(true);
                    }
                }
                addFrinend.setVisibility(View.VISIBLE);
            } else {
                Toast.showShortToast("没有此用户");
            }
        }
    };


    private final Handler addFriendHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Tag.SUCCESS) {
                String message = (String) msg.obj;
                Toast.showShortToast(message);
            } else if (msg.what == Tag.FAILURE) {
                String message = (String) msg.obj;
                Toast.showShortToast(message);
            } else if (msg.what == Tag.OTHER) {
                Toast.showShortToast("请检查网络");
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query:
                queryUser = editText.getText().toString().trim();
                Dao.getUserInfo(SPUtil.getData("username"), queryUser, UserInfohandler);
                break;
            case R.id.add_friend:
                Dao.addFriend(queryUser, addFriendHandler);
                break;
        }
    }

    public void getFriendInfo() {
        try {
            List<Friend> list = Friend.getDao().queryForAll();
            Iterator<Friend> it = list.iterator();
            while (it.hasNext()) {
                friend = it.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
