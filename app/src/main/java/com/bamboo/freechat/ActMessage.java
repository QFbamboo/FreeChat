package com.bamboo.freechat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bamboo.base.BaseActivity;
import com.bamboo.base.ContentView;
import com.bamboo.base.ViewInject;
import com.bamboo.common.Dao;
import com.bamboo.bean.Msg;
import com.bamboo.common.Tag;
import com.bamboo.dialog.DialogView;
import com.bamboo.util.DateUtil;
import com.bamboo.util.ImgHelper;

/**
 * Created by bamboo on 16-6-3.
 */

@ContentView(R.layout.act_message)
public class ActMessage extends BaseActivity {

    @ViewInject(R.id.apply_userName)
    private TextView textView;
    @ViewInject(R.id.apply_userImage)
    private ImageView imageView;
    @ViewInject(R.id.apply_date)
    private TextView applyDate;
    @ViewInject(R.id.apply_confirm)
    private Button confirm;
    @ViewInject(R.id.apply_cancel)
    private Button cancel;
    @ViewInject(R.id.title)
    private ActTitle title;

    private Msg msg;

    private static final long oneDayTimes = 24 * 60 * 60 * 1000L;
    private static long currentTimes = System.currentTimeMillis();
    private String sub_date = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title.setTitleName("好友请求");

        msg = (Msg) getIntent().getSerializableExtra("msg");

        String date = DateUtil.getDateToString(msg.getAdd_time());

        if (currentTimes - msg.getAdd_time() < oneDayTimes) {
            sub_date = date.substring(11, date.length());
        } else {
            sub_date = date.substring(9, 14);
        }

        applyDate.setText(sub_date);
        textView.setText(msg.getFromUserName());
//        new LoadPictrue(ActMessage.this, msg.getFromAvatar(), imageView);
        ImgHelper.setImage(imageView, msg.getFromAvatar());

        if (msg.getFlag() == 2) {
            confirm.setText("已允许");
            confirm.setClickable(false);
            cancel.setClickable(false);
        } else if (msg.getFlag() == 3) {
            cancel.setText("已拒绝");
            confirm.setClickable(false);
            cancel.setClickable(false);
        }
    }

    private final Handler confirmHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Tag.SUCCESS) {
                Toast.makeText(ActMessage.this, "已接受好友请求", Toast.LENGTH_SHORT).show();
                confirm.setText("已允许");
                confirm.setClickable(false);
                cancel.setClickable(false);
            } else if (msg.what == Tag.FAILURE) {
                Toast.makeText(ActMessage.this, "接受好友请求失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == Tag.OTHER) {
                Toast.makeText(ActMessage.this, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private final Handler cancelHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Tag.SUCCESS) {
                Toast.makeText(ActMessage.this, "已接受好友请求", Toast.LENGTH_SHORT).show();
                cancel.setText("已允许");
                confirm.setClickable(false);
                cancel.setClickable(false);
            } else if (msg.what == Tag.FAILURE) {
                Toast.makeText(ActMessage.this, "接受好友请求失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == Tag.OTHER) {
                Toast.makeText(ActMessage.this, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apply_confirm:
                if (msg.getFlag() == 0) {
                    DialogView.showDialog(ActMessage.this, "点击确认后同意好友请求", new DialogView.OnClickListener() {

                        @Override
                        public void onClick(DialogView dialogView) {
                            Dao.friendRequest(msg.getId(), confirmHandler);
                        }
                    });
                }
                break;
            case R.id.apply_cancel:
                if (msg.getFlag() == 0) {
                    DialogView.showDialog(ActMessage.this, "点击确认后拒绝好友请求", new DialogView.OnClickListener() {

                        @Override
                        public void onClick(DialogView dialogView) {
                            Dao.friendReject(msg.getId(), cancelHandler);
                        }
                    });
                }
                break;
        }
    }
}
