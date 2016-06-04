package com.bamboo.freechat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bamboo.base.BaseFragment;
import com.bamboo.base.ContentView;
import com.bamboo.base.LoadPictrue;
import com.bamboo.base.MyAdapter;
import com.bamboo.base.ViewInject;
import com.bamboo.common.Dao;
import com.bamboo.common.Msg;
import com.bamboo.common.Tag;
import com.bamboo.util.DateUtil;
import com.jiangKlijna.pulltorefreshswipemenu.swipemenu.SwipeMenu;
import com.jiangKlijna.pulltorefreshswipemenu.swipemenu.SwipeMenuItem;
import com.jiangKlijna.pulltorefreshswipemenu.swipemenu.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bamboo on 16-6-3.
 */
@ContentView(R.layout.frag_message)
public class FragMessage extends BaseFragment
        implements SwipeMenuListView.OnMenuItemClickListener,
        SwipeMenuListView.SwipeMenuCreator,SwipeRefreshLayout.OnRefreshListener {

    @ViewInject(R.id.lv_content)
    private SwipeMenuListView listView;
    @ViewInject(R.id.lv_refresh)
    private SwipeRefreshLayout refresh;

    private List<Msg> list;
    private MyAdapter<Msg> adapter;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        adapter = new MyAdapter<Msg>(getActivity(), new ArrayList<Msg>()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = getHolder(getContext(),
                        convertView, parent, R.layout.msg_content, position);
                ImageView imageView = holder.getView(R.id.msgImage);
                TextView textView = holder.getView(R.id.msgText);
                new LoadPictrue(getContext(), list.get(position).getFromAvatar(), imageView);
                textView.setText(list.get(position).getFromUserName());
                TextView show_status = holder.getView(R.id.msgTime);

                int flag = list.get(position).getFlag();

                long time = list.get(position).getAdd_time();
                String time_str = DateUtil.getDateToString(time);
                String sub_time = time_str.substring(11, time_str.length());

                if (flag == 0) {
                    show_status.setText(sub_time);
                } else if (flag == 2) {
                    show_status.setText("已同意");
                } else if (flag == 3) {
                    show_status.setText("已拒绝");
                }

                return holder.getConvertView();
            }
        };
        onRefresh();
        //此处设置listView的适配器
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        refresh.setOnRefreshListener(this);
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Msg msg = adapter.getItem(position);
        startActivity(new Intent(getActivity(),
                ActMessage.class).putExtra("msg", msg));
    }

    @Override
    public void onMenuItemClick(int position, SwipeMenu menu, int index) {

    }

    @Override
    public void create(SwipeMenu menu, int position) {
        SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
        deleteItem.setWidth(80);
        deleteItem.setTitle("删除");
        deleteItem.setTitleSize(18);
        deleteItem.setTitleColor(Color.WHITE);
        deleteItem.setBackground(new ColorDrawable(0xFFF93F25));
        menu.addMenuItem(deleteItem);
    }


    @Override
    public void onRefresh() {
        Dao.getMessage(new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                if (msg.what == Tag.SUCCESS) {
                    list = (List<Msg>) msg.obj;
                    adapter.setData(list);
                } else {
                    Toast.makeText(getActivity(), "再按一次确认退出",
                            Toast.LENGTH_SHORT).show();
                }
                refresh.setRefreshing(false);
            }
        });
    }
}
