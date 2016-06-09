package com.bamboo.common;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.bamboo.freechat.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by caojiang on 4/18/2016.
 * FragemntManager
 */
public class FM {
    public  final List<Fragment> fragments = new ArrayList();
    private final Class<? extends Fragment>[] claszs;
    private final FragmentActivity act;
    private final int frgContentRes;

    public FM(FragmentActivity act, @IdRes int frgContentRes, Class<? extends Fragment>... clasz) {
        this.act = act;
        this.frgContentRes = frgContentRes;
        claszs = clasz;
        recycle();
    }

    private Fragment nowFragment;
    private int nowtab;

    public boolean selectTab(int tab) {
        Fragment from = fragments.get(nowtab), to = fragments.get(tab);
        if (nowFragment != to) {
            FragmentTransaction transaction = act.getSupportFragmentManager().beginTransaction();
            if (nowtab < tab) {
                transaction.setCustomAnimations(R.anim.tran_nex_in, R.anim.tran_nex_out);
            } else {
                transaction.setCustomAnimations(R.anim.tran_pre_in, R.anim.tran_pre_out);
            }
            if (nowFragment == null) { // 第一次加载
                transaction.replace(frgContentRes, to);
            } else if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(frgContentRes, to);
            } else {
                transaction.hide(from).show(to); // 隐藏当前的fragment，显示下一个
            }
            transaction.commit();
            nowFragment = to;
            nowtab = tab;
            return true;
        }
        return false;
    }

    //
    public void recycle() {
        nowtab = 0;
        nowFragment = null;
        fragments.clear();
        try {
            for (Class<? extends Fragment> clasz : claszs)
                fragments.add(clasz.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
