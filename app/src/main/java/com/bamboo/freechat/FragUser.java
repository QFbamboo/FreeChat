package com.bamboo.freechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.bamboo.base.BaseFragment;
import com.bamboo.base.ContentView;
import com.bamboo.base.ViewInject;
import com.bamboo.bean.Friend;
import com.bamboo.common.Dao;
import com.bamboo.common.Tag;
import com.bamboo.common.Url;
import com.bamboo.dialog.DialogView;
import com.bamboo.util.IMUtil;
import com.bamboo.util.ImgHelper;
import com.bamboo.util.IntentUtil;
import com.bamboo.util.SPUtil;
import com.bamboo.util.Toast;
import com.bamboo.view.AvatorView;
import com.bamboo.view.TitleView;

import java.io.File;

/**
 * Created by bamboo on 16-6-1.
 */
@ContentView(R.layout.user)
public class FragUser extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener {

    public static final int REQUEST_PIC = 0XF;
    public static final int CROP_PIC = 0XA;

    @ViewInject(R.id.userImage)
    private AvatorView userImage;
    @ViewInject(R.id.userName)
    private TextView userName;
    @ViewInject(R.id.title)
    private TitleView title;

    private String avatar_path = SPUtil.getData("avatar");

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setTitleName("用户信息");
        title.setGoneBack();
        String username = SPUtil.getData("username");
        ImgHelper.setImage(userImage, avatar_path);
        userName.setText(username);//显示用户名

    }

    private final Handler exitHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Tag.SUCCESS) {
                try {

                    SPUtil.clearData();
                    //清空本地好友数据
                    Friend.getDao().delete(Friend.getDao().queryForAll());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getActivity().finish();
                Toast.showShortToast("已退出");
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
                        IMUtil.logOut(exitHandler);
                    }
                });
                break;
            case R.id.search_user:
                startActivity(new Intent(getActivity(), ActSearch.class));
                break;
            case R.id.userImage:
                startActivityForResult(IntentUtil.getPicIntent(), REQUEST_PIC);
                break;
        }
    }

    File corpPic;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PIC) {
                String fileName = IntentUtil.getPicPath(getActivity(), data);
                startActivityForResult(IntentUtil.getAvaIntent(new File(fileName),
                        corpPic = ImgHelper.getNewImgFile()), CROP_PIC);
            } else if (requestCode == CROP_PIC) {
                Dao.uploadAvatar(corpPic, new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == Tag.SUCCESS) {

                            Dao.upAvatarData(Url.UPLOAD_AVATAR + corpPic.getName(), new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    if (msg.what == Tag.SUCCESS) {
                                        Toast.showShortToast("上传成功");
                                        SPUtil.setDate("avatar", Url.UPLOAD_AVATAR + corpPic.getName());
//                                        ImgHelper.setImage(userImage, avatar_path);
                                        ImgHelper.setImage(userImage, "file://" + corpPic.getAbsolutePath());
                                    } else {
                                        Toast.showShortToast("更新失败");
                                    }
                                }
                            });
                        } else {
                            Toast.showShortToast("上传失败");
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onRefresh() {

    }
}
