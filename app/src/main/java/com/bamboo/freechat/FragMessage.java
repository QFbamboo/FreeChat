package com.bamboo.freechat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bamboo.base.BaseFragment;
import com.bamboo.base.ContentView;
import com.bamboo.base.LoadPictrue;
import com.bamboo.base.MyAdapter;
import com.bamboo.base.ViewInject;
import com.bamboo.common.Dao;
import com.bamboo.common.Message;
import com.bamboo.common.Tag;
import com.bamboo.util.DateUtil;
import com.bamboo.util.SPUtil;

import java.util.List;

/**
 * Created by bamboo on 16-6-3.
 */
@ContentView(R.layout.guide)
public class FragMessage extends BaseFragment {

    @ViewInject(R.id.lv_content)
    private ListView listView;
    private List<Message> list;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MyAdapter<Message> adapter = new MyAdapter<Message>(getActivity(), list) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = getHolder(getContext(),
                        convertView, parent, R.layout.msg_content, position);
                ImageView imageView = holder.getView(R.id.msgImage);
                TextView textView = holder.getView(R.id.msgText);
                new LoadPictrue(getContext(), list.get(position).getFromAvatar(), imageView);
                textView.setText(list.get(position).getFromUserName());
                TextView msgTime = holder.getView(R.id.msgTime);
                long time = list.get(position).getAdd_time();
                String time_str = DateUtil.getDateToString(time);
                String sub_time = time_str.substring(11, time_str.length());
                msgTime.setText(sub_time);
//                            TextView status = holder.getView(R.id.msgStatus);
//                            int flag = list.get(position).getFlag();
//                            if (flag == 0) {
//                                status.setText("同意");
//                            } else if (flag == 2) {
//                                status.setText("已同意");
//                            } else if (flag == 3) {
//                                status.setText("已拒绝");
//                            }

                return holder.getConvertView();
            }
        };


        //此处设置listView的适配器
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);


    }

    //给listView中的各个元素设值
    public List<Message> setMessageAdapter() {
        String username = SPUtil.getDate("username");
        Dao.getMessage(username, new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                if (msg.what == Tag.SUCCESS) {
                    list = (List<Message>) msg.obj;
                } else {
                    Toast.makeText(getContext(), "再按一次确认退出",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return list;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), ActMessage.class));
                break;
        }

    }
}
