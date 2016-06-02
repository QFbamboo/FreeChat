package com.bamboo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

import com.bamboo.bean.User;

/**
 * Created by bamboo on 16-6-1.
 */
public class SPUtil {

    public static SharedPreferences spUtil;

    public synchronized static final void getSpUtil(Context context) {
        if (null == spUtil) {
            spUtil = context.getSharedPreferences("config", 0);
        }
    }

    public static void setDate(String key, String value) {
        SharedPreferences.Editor editor = spUtil.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setUser(User user) {
        SharedPreferences.Editor editor = spUtil.edit();
        editor.putInt("userid", user.getUserid());
        editor.putString("username", user.getUsername());
        editor.putString("avatar", user.getAvatar());
        editor.commit();
    }

    public static String getDate(String key) {
        String value = spUtil.getString(key, "");
        return value;
    }

    public static void clearData() {
        spUtil.edit().clear().commit();
    }
}
