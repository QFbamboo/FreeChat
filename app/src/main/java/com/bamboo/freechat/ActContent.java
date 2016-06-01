package com.bamboo.freechat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bamboo.base.BaseActivity;
import com.bamboo.base.ContentView;
import com.bamboo.base.ViewInject;
import com.bamboo.common.Dao;
import com.bamboo.common.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bamboo on 16-5-30.
 */

@ContentView(R.layout.act_content)
public class ActContent extends BaseActivity {

    @ViewInject(R.id.viewpager)
    private ViewPager MyPager;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    String[] arr1 = {"haha", "lala", "heihei"};
    String[] arr2 = {"hello", "windy", "bamboo"};
    String[] arr3 = {"fengfeng", "xixi", "lulala"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitViewPager();
    }

    public void getUserInfo() {
        Dao.getUserInfo("", "", new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == Tag.SUCCESS) {

                }
            }
        });
    }


    //初始化viewPager
    public void InitViewPager() {
        fragmentList.add(new ActMessageFragment().newIntence(arr1));
        fragmentList.add(new ActMessageFragment().newIntence(arr2));
        fragmentList.add(new ActMessageFragment().newIntence(arr3));
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
        switch (v.getId()) {
            case R.id.tvChat:
                MyPager.setCurrentItem(0);
                break;
            case R.id.tvMsg:
                MyPager.setCurrentItem(1);
                break;
            case R.id.tvUser:
                MyPager.setCurrentItem(2);
                break;
        }
    }
}
