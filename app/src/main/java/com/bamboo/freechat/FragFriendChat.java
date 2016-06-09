package com.bamboo.freechat;

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

import com.alibaba.mobileim.conversation.IYWConversationListener;
import com.alibaba.mobileim.conversation.YWConversation;
import com.bamboo.base.BaseFragment;
import com.bamboo.base.ContentView;
import com.bamboo.base.MyAdapter;
import com.bamboo.base.ViewInject;
import com.bamboo.bean.Friend;
import com.bamboo.common.Dao;
import com.bamboo.common.Tag;
import com.bamboo.dialog.DialogView;
import com.bamboo.util.DateUtil;
import com.bamboo.util.IMUtil;
import com.bamboo.util.ImgHelper;
import com.bamboo.util.Toast;
import com.bamboo.view.AvatorView;
import com.bamboo.view.TitleView;
import com.jiangKlijna.pulltorefreshswipemenu.swipemenu.SwipeMenu;
import com.jiangKlijna.pulltorefreshswipemenu.swipemenu.SwipeMenuItem;
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
    @ViewInject(R.id.title)
    private TitleView title;
    private List<Friend> lists;
    private MyAdapter<Friend> adapter;
    //    = 0000000000000L

    private static final long oneDayTimes = 24 * 60 * 60 * 1000L;
    private static long currentTimes = System.currentTimeMillis();
    private long times = System.currentTimeMillis();

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setTitleName("朋友会话");
        title.setGoneBack();


        initAdapter();
        onRefresh();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        refresh.setOnRefreshListener(this);
        refresh.setColorSchemeColors(Tag.RefreshColors);
        IMUtil.AddIYWConversationServiceListener(this);
        listView.setMenuCreator(this);
        listView.setOnMenuItemClickListener(this);
    }

    public void initAdapter() {
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
                        msgDate.setText(DateUtil.getDateToString(times).substring(11, 16));
                    } else {
                        msgDate.setText(DateUtil.getDateToString(times).substring(0, 10));
                    }

                    int unread = conversation.getUnreadCount();
                    if (unread != 0) {
                        msg.setText("" + unread);
                        msg.setVisibility(View.VISIBLE);
                    } else {
                        msg.setVisibility(View.GONE);
                    }

                } else {
                    msgDate.setText(DateUtil.getDateToString(times).substring(11, 16));
                    content.setText("");
                }

                String avatar_path = friend.getAvatar();
                ImgHelper.setImage(imageView, avatar_path);
                String userName = friend.getUsername();
                friendName.setText(userName); //设置用户名
                return holder.getConvertView();
            }
        };
        lists = Friend.getInstances();
        adapter.setData(lists);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        IMUtil.RemoveIYWConversationServiceListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Friend friends = adapter.getItem(position);
        IMUtil.startChatAct(getActivity(), friends.getUsername());
    }

    @Override
    public void onMenuItemClick(final int position, SwipeMenu menu, int index) {
//        Toast.showShortToast("fragFriend");
        DialogView.showDialog(getActivity(), "点击确认后删除此好友", new DialogView.OnClickListener() {
            @Override
            public void onClick(DialogView dialogView) {
                final Friend friend = adapter.getItem(position);
                final String friendName = friend.getUsername();
                Dao.deleteFriend(friendName, new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == Tag.SUCCESS) {
                            try {
                                Friend.getDao().delete(friend);
                                Toast.showShortToast("刪除好友成功");

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.showShortToast("刪除好友失敗");
                            }
                        } else {
                            Toast.showShortToast("刪除好友失敗");
                        }
                    }
                });
            }
        });
    }

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

    //下拉刷新
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
