package com.bamboo.freechat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.bamboo.base.BaseActivity;
import com.bamboo.base.BaseFragment;
import com.bamboo.base.ContentView;
import com.bamboo.base.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bamboo on 16-5-30.
 */

@ContentView(R.layout.act_content)
public class ActContent extends BaseActivity {

    @ViewInject(R.id.viewpager)
    private ViewPager MyPager;
    private List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();

    String[] arr1 = {"haha", "lala", "heihei"};
    String[] arr2 = {"hello", "windy", "bamboo"};
    String[] arr3 = {"fengfeng", "xixi", "lulala"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitViewPager();
    }

    //初始化viewPager
    public void InitViewPager() {
        fragmentList.add(new ActMessageFragment().newIntence(arr1));
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
}
