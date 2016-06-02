package com.bamboo.freechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bamboo.base.BaseFragment;
import com.bamboo.base.ContentView;
import com.bamboo.base.LoadPictrue;
import com.bamboo.base.ViewInject;
import com.bamboo.common.Tag;
import com.bamboo.dialog.DialogView;
import com.bamboo.util.IMUtil;
import com.bamboo.util.SPUtil;

/**
 * Created by bamboo on 16-6-1.
 */
@ContentView(R.layout.user)
public class FragUser extends BaseFragment {

    @ViewInject(R.id.userImage)
    private ImageView userImage;
    @ViewInject(R.id.userName)
    private TextView userName;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String username = SPUtil.getDate("username");
        String avatar_path = SPUtil.getDate("avatar");
        new LoadPictrue(getActivity(), avatar_path, userImage);//显示头像
        userName.setText(username);//显示用户名


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Tag.SUCCESS) {
                SPUtil.clearData();
                ((Activity) getContext()).finish();
                Toast.makeText(getContext(), "已退出", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logOut:   //设置退出登录按钮
                DialogView.showDialog(getActivity(), "点击确定退出应用！", new DialogView.OnClickListener() {
                    @Override
                    public void onClick(DialogView dialogView) {
                        IMUtil.logOut(handler);
                    }
                });
                break;
            case R.id.search_info:
                startActivity(new Intent(getActivity(), ActSearch.class));
                break;
        }


    }
}
