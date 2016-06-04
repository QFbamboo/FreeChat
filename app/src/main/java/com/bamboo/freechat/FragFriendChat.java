package com.bamboo.freechat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.bamboo.common.Friend;
import com.bamboo.common.Tag;
import com.bamboo.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bamboo on 16-6-2.
 */
@ContentView(R.layout.frag_message)
public class FragFriendChat extends BaseFragment {

    @ViewInject(R.id.lv_content)
    private ListView listView;

    private List<Friend> list;
    private MyAdapter<Friend> adapter;
    private static final String username = SPUtil.getDate("username");

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setFriendList();
        adapter = new MyAdapter<Friend>(getActivity(), new ArrayList<Friend>()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = getHolder(getContext(), convertView
                        , parent, R.layout.msg_content, position);

                ImageView imageView = holder.getView(R.id.msgImage);
                String avatar_path = list.get(position).getAvatar();
                new LoadPictrue(getContext(), avatar_path, imageView);//设置头像

                TextView textView = holder.getView(R.id.msgText);
                String userName = list.get(position).getUsername();
                textView.setText(userName); //设置用户名
                return holder.getConvertView();
            }
        };

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    public void setFriendList() {
        Dao.getFriendList(username, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == Tag.SUCCESS) {
                    Toast.makeText(getActivity(), "获取好友列表成功", Toast.LENGTH_SHORT).show();
                    list = (List<Friend>) msg.obj;
                    adapter.setData(list);
                } else {
                    Toast.makeText(getActivity(), "获取好友列表失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String avatar_path = adapter.getItem(position).getAvatar();
        String userName = adapter.getItem(position).getUsername();
        if (position < listView.getCount()) {
            Intent intent = new Intent();
            intent.putExtra("avatar_path", avatar_path);
            intent.putExtra("userName", userName);
            startActivity(intent);
        }
    }
}
