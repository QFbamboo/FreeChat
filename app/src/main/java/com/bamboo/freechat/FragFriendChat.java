package com.bamboo.freechat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.mobileim.conversation.IYWConversationListener;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.lib.presenter.conversation.Conversation;
import com.bamboo.base.BaseFragment;
import com.bamboo.base.ContentView;
import com.bamboo.util.DateUtil;
import com.bamboo.util.ImgHelper;
import com.bamboo.base.MyAdapter;
import com.bamboo.base.ViewInject;
import com.bamboo.common.Dao;
import com.bamboo.bean.Friend;
import com.bamboo.common.Tag;
import com.bamboo.util.IMUtil;
import com.bamboo.view.AvatorView;
import com.jiangKlijna.pulltorefreshswipemenu.swipemenu.SwipeMenu;
import com.jiangKlijna.pulltorefreshswipemenu.swipemenu.SwipeMenuListView;

import java.util.List;

/**
 * Created by bamboo on 16-6-2.
 */
@ContentView(R.layout.frag_message)
public class FragFriendChat extends BaseFragment
        implements SwipeMenuListView.OnMenuItemClickListener
        , SwipeMenuListView.SwipeMenuCreator, SwipeRefreshLayout.OnRefreshListener
        , IYWConversationListener {

    @ViewInject(R.id.lv_content)
    private SwipeMenuListView listView;
    @ViewInject(R.id.lv_refresh)
    private SwipeRefreshLayout refresh;

    private List<Friend> lists;
    private MyAdapter<Friend> adapter;

    private long times = 0000000000000L;
    private static final long oneDayTimes = 24 * 60 * 60 * 1000L;
    private static long currentTimes = System.currentTimeMillis();
    private String sub_date = "";


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        adapter = new MyAdapter<Friend>(getActivity()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = getHolder(getContext(), convertView
                        , parent, R.layout.friend_list, position);
                AvatorView imageView = holder.getView(R.id.friend_Image);
                TextView friendName = holder.getView(R.id.friend_name);
                TextView content = holder.getView(R.id.last_content);
                TextView msg = holder.getView(R.id.last_times);
                TextView msgDate = holder.getView(R.id.last_date);

                Friend friend = adapter.getItem(position);

                YWConversation conversation = IMUtil.getCs().getConversationByUserId(friend.getUsername());
                if (conversation != null) {
                    String lastContent = conversation.getLatestContent();
                    content.setText(lastContent);
                    times = conversation.getLatestTimeInMillisecond();
                    if (currentTimes - times < oneDayTimes) {
                        msgDate.setText(DateUtil.getDateToString(times).substring(11, 17));
                    } else {
                        msgDate.setText(DateUtil.getDateToString(times).substring(9, 14));
                    }

                    int unread = conversation.getUnreadCount();
                    if (unread != 0) {
                        msg.setText("" + unread);
                        msg.setVisibility(view.VISIBLE);
                    } else {
                        msg.setVisibility(View.GONE);
                    }

                }

                String avatar_path = friend.getAvatar();
                ImgHelper.setImage(imageView, avatar_path);
                String userName = friend.getUsername();
                friendName.setText(userName); //设置用户名
                return holder.getConvertView();
            }
        };
        onRefresh();
        lists = Friend.getInstances();
        adapter.setData(lists);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        refresh.setOnRefreshListener(this);
        refresh.setColorSchemeColors(Tag.RefreshColors);
        IMUtil.AddIYWConversationServiceListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Friend friends = adapter.getItem(position);
        IMUtil.startChatAct(getActivity(), friends.getUsername());
//        startActivity(new Intent(getActivity(), ActChartRoom.class).putExtra("friends", friends));
    }

    @Override
    public void onMenuItemClick(int position, SwipeMenu menu, int index) {

    }

    @Override
    public void create(SwipeMenu menu, int position) {

    }

    @Override
    public void onRefresh() {
        Dao.getFriendList(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == Tag.SUCCESS) {
                    try {
                        List<Friend> list = (List<Friend>) msg.obj;
                        adapter.setData(list);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                refresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onItemUpdated() {
        adapter.notifyDataSetChanged();
    }
}
