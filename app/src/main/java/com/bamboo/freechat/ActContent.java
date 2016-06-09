package com.bamboo.freechat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bamboo.base.BaseActivity;
import com.bamboo.base.BaseFragment;
import com.bamboo.base.ContentView;
import com.bamboo.base.ViewInject;
import com.bamboo.common.FM;
import com.bamboo.util.ImgHelper;
import com.bamboo.util.Toast;

/**
 * Created by bamboo on 16-5-30.
 */

@ContentView(R.layout.act_content)
public class ActContent extends BaseActivity{

    private static final int minSize = ImgHelper.dp_px(38);
    private static final int maxSize = ImgHelper.dp_px(50);
//    @ViewInject(R.id.viewpager)
//    private ViewPager MyPager;
    @ViewInject(R.id.tvChat)
    private TextView chat;
    @ViewInject(R.id.tvMsg)
    private TextView msg;
    @ViewInject(R.id.tvUser)
    private TextView user;

    @ViewInject(R.id.viewImage1)
    private ImageView pager1;
    @ViewInject(R.id.viewImage2)
    private ImageView pager2;
    @ViewInject(R.id.viewImage3)
    private ImageView pager3;

    //    private List<BaseFragment> fragmentList = new ArrayList<>();
    private FM fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitViewPager();

    }

    //初始化viewPager
    public void InitViewPager() {
        fm = new FM(this, R.id.frag_content, FragFriendChat.class,
                FragMessage.class, FragUser.class);
        setCurrentItem(0);

 //        MyPager.setOnPageChangeListener(this);
//        fragmentList.add(new FragFriendChat());
//        fragmentList.add(new FragMessage());
//        fragmentList.add(new FragUser());
////        FragmentManager fragmentManager = getSupportFragmentManager();
//        MyPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//
//            @Override
//            public int getCount() {
//                return fragmentList.size();
//            }
//
//            @Override
//            public Fragment getItem(int arg0) {
//                return fragmentList.get(arg0);
//            }
//
//        });
//        MyPager.setCurrentItem(0);
    }


    @Override
    public void onClick(View v) {
        for (Fragment fragment : fm.fragments) {
            ((BaseFragment)fragment).onClick(v);
        }
        switch (v.getId()) {
            case R.id.viewImage1:
            case R.id.tvChat:
                setCurrentItem(0);
                break;
            case R.id.viewImage2:
            case R.id.tvMsg:
                setCurrentItem(1);
                break;
            case R.id.viewImage3:
            case R.id.tvUser:
                setCurrentItem(2);
                break;
        }
    }

    private long firstClick = 0;

    //重写返回键的方法
    @Override
    public void onBackPressed() {
        if (firstClick == 0) {
            Toast.showShortToast("再按一次确认退出");
            firstClick = System.currentTimeMillis();
        } else {
            long secondClick = System.currentTimeMillis();
            if (secondClick - firstClick < 1000) {
                finish();
            } else {
                Toast.showShortToast("再按一次确认退出");
                firstClick = 0;
            }
        }
    }

    public void setCurrentItem(int i) {
        fm.selectTab(i);
        if (i == 0) {
            chat.setTextColor(0x88ff00ff);
            msg.setTextColor(0xff000000);
            user.setTextColor(0xff000000);

            ViewGroup.LayoutParams param1 = pager1.getLayoutParams();
            param1.height = maxSize;
            param1.width = maxSize;
            pager1.setLayoutParams(param1);

            ViewGroup.LayoutParams params2 = pager2.getLayoutParams();
            params2.height = minSize;
            params2.width = minSize;
            pager2.setLayoutParams(params2);

            ViewGroup.LayoutParams params3 = pager3.getLayoutParams();
            params3.height = minSize;
            params3.width = minSize;
            pager3.setLayoutParams(params3);

        } else if (i == 1) {
            msg.setTextColor(0x88ff00ff);
            chat.setTextColor(0xff000000);
            user.setTextColor(0xff000000);

            ViewGroup.LayoutParams param1 = pager1.getLayoutParams();
            param1.height = minSize;
            param1.width = minSize;
            pager1.setLayoutParams(param1);

            ViewGroup.LayoutParams params2 = pager2.getLayoutParams();
            params2.height = maxSize;
            params2.width = maxSize;
            pager2.setLayoutParams(params2);

            ViewGroup.LayoutParams params3 = pager3.getLayoutParams();
            params3.height = minSize;
            params3.width = minSize;
            pager3.setLayoutParams(params3);
        } else if (i == 2) {
            user.setTextColor(0x88ff00ff);
            chat.setTextColor(0xff000000);
            msg.setTextColor(0xff000000);

            ViewGroup.LayoutParams param1 = pager1.getLayoutParams();
            param1.height = minSize;
            param1.width = minSize;
            pager1.setLayoutParams(param1);

            ViewGroup.LayoutParams params2 = pager2.getLayoutParams();
            params2.height = minSize;
            params2.width = minSize;
            pager2.setLayoutParams(params2);

            ViewGroup.LayoutParams params3 = pager3.getLayoutParams();
            params3.height = maxSize;
            params3.width = maxSize;
            pager3.setLayoutParams(params3);
        }
    }
}
