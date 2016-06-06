package com.bamboo.freechat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bamboo.base.BaseActivity;
import com.bamboo.base.BaseFragment;
import com.bamboo.base.ContentView;
import com.bamboo.base.ViewInject;
import com.bamboo.util.ImgHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bamboo on 16-5-30.
 */

@ContentView(R.layout.act_content)
public class ActContent extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final int minSize = ImgHelper.dp_px(38);
    private static final int maxSize = ImgHelper.dp_px(50);
    @ViewInject(R.id.viewpager)
    private ViewPager MyPager;
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

    private List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitViewPager();
        MyPager.setOnPageChangeListener(this);
    }

    //初始化viewPager
    public void InitViewPager() {
        fragmentList.add(new FragFriendChat());
        fragmentList.add(new FragMessage());
        fragmentList.add(new FragUser());
//        FragmentManager fragmentManager = getSupportFragmentManager();
        MyPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return fragmentList.get(arg0);
            }

        });
        MyPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        for (BaseFragment fragment : fragmentList) {
            fragment.onClick(v);
        }
        switch (v.getId()) {
            case R.id.viewImage1:
                MyPager.setCurrentItem(0);
                break;
            case R.id.viewImage2:
                MyPager.setCurrentItem(1);
                break;
            case R.id.viewImage3:
                MyPager.setCurrentItem(2);
        }
    }

    private long firstClick = 0;

    @Override
    public void onBackPressed() {//重写返回键的方法
        if (firstClick == 0) {
            Toast.makeText(ActContent.this, "再按一次确认退出",
                    Toast.LENGTH_SHORT).show();
            firstClick = System.currentTimeMillis();
        } else {
            long secondClick = System.currentTimeMillis();
            if (secondClick - firstClick < 1000) {
                finish();
            } else {
                Toast.makeText(ActContent.this, "再按一次确认退出",
                        Toast.LENGTH_SHORT).show();
                firstClick = 0;
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (MyPager.getCurrentItem() == 0) {
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

        } else if (MyPager.getCurrentItem() == 1) {
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
        } else if (MyPager.getCurrentItem() == 2) {
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
