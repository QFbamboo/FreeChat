package com.bamboo.freechat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.bamboo.base.BaseFragment;
import com.bamboo.base.ContentView;
import com.bamboo.base.MyAdapter;
import com.bamboo.base.ViewInject;
import com.bamboo.common.Dao;
import com.bamboo.bean.Msg;
import com.bamboo.common.Tag;
import com.bamboo.dialog.DialogView;
import com.bamboo.util.DateUtil;
import com.bamboo.util.ImgHelper;
import com.bamboo.util.Toast;
import com.bamboo.view.AvatorView;
import com.bamboo.view.TitleView;
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
        SwipeMenuListView.SwipeMenuCreator, SwipeRefreshLayout.OnRefreshListener {

    @ViewInject(R.id.lv_content)
    private SwipeMenuListView listView;

    @ViewInject(R.id.lv_refresh)
    private SwipeRefreshLayout refresh;

    private List<Msg> list;
    private MyAdapter<Msg> adapter;
    @ViewInject(R.id.title)
    private TitleView title;
    private static final long oneDayTimes = 24 * 60 * 60 * 1000L;
    private static long currentTimes = System.currentTimeMillis();
    private String sub_time = "";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setTitleName("消息列表");
        title.setGoneBack();
        adapter = new MyAdapter<Msg>(getActivity(), new ArrayList<Msg>()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = getHolder(getContext(),
                        convertView, parent, R.layout.msg_content, position);
                AvatorView imageView = holder.getView(R.id.msgImage);
                TextView textView = holder.getView(R.id.msgText);
//                new LoadPictrue(getContext(), list.get(position).getFromAvatar(), imageView);
                ImgHelper.setImage(imageView, list.get(position).getFromAvatar());
                textView.setText(list.get(position).getFromUserName());
                TextView show_status = holder.getView(R.id.msgTime);

                int flag = list.get(position).getFlag();

                long time = list.get(position).getAdd_time();
                String time_str = DateUtil.getDateToString(time);

                if (currentTimes - time < oneDayTimes) {
                    sub_time = time_str.substring(11, 16);
                } else {
                    sub_time = time_str.substring(0, 10);
                }
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
        //设置下拉刷新的监听
        refresh.setOnRefreshListener(this);
        refresh.setColorSchemeColors(Tag.RefreshColors);
        listView.setMenuCreator(this);
        listView.setOnMenuItemClickListener(this);
    }

    //点击列表时，进入ActMessage布局，并把Msg对象（已实现序列化）传过去
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Msg msg = adapter.getItem(position);
        startActivity(new Intent(getActivity(),
                ActMessage.class).putExtra("msg", msg));
    }

    @Override
    public void onMenuItemClick(final int position, SwipeMenu menu, int index) {
//        Toast.showShortToast("fragMessage");
       DialogView.showDialog(getActivity(), "点击确认后删除此消息", new DialogView.OnClickListener() {
           @Override
           public void onClick(DialogView dialogView) {
               int msgId=adapter.getItem(position).getId();
               Dao.deleteMessage(msgId,new Handler(){
                   @Override
                   public void handleMessage(Message msg) {
                       if (msg.what==Tag.SUCCESS){
                           Toast.showShortToast("删除成功");
                       }else {
                           Toast.showShortToast("删除失败");
                       }
                   }
               });
           }
       });
    }

    //单项列表的滑动操作
    @Override
    public void create(SwipeMenu menu, int position) {
        SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
        deleteItem.setWidth(ImgHelper.dp_px(80));
        deleteItem.setTitle("删除");
        deleteItem.setTitleSize(18);
        deleteItem.setTitleColor(Color.WHITE);
        deleteItem.setBackground(new ColorDrawable(0xFFF93F25));
        menu.addMenuItem(deleteItem);
    }

    //列表下拉刷新
    @Override
    public void onRefresh() {
        Dao.getMessage(new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                if (msg.what == Tag.SUCCESS) {
                    list = (List<Msg>) msg.obj;
                    adapter.setData(list);
                }
                refresh.setRefreshing(false);
            }
        });
    }
}
