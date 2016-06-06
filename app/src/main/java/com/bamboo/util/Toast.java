package com.bamboo.util;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by ilioili on 2014/12/31.
 */
public class Toast {
    private static Context context;

    private static android.widget.Toast toast;

    private static TextView tvContent;

    public static void init(Context c) {
        context = c.getApplicationContext();
    }

    public static void showLongToast(@Nullable Context context, Object obj) {
        showToast(context, obj, android.widget.Toast.LENGTH_LONG);
    }

    public static void showToast(@Nullable Context context, @Nullable Object obj, int time) {
        if (null == context || obj == null) {
            return;
        }
        if (toast == null) {
            toast = new android.widget.Toast(context);
            toast.setDuration(time);
            toast.setGravity(Gravity.CENTER, 0, 0);
            tvContent = new TextView(context);
            tvContent.setTextColor(Color.WHITE);
            tvContent.setGravity(Gravity.CENTER);
            tvContent.setBackgroundColor(Color.argb(200, 0, 0, 0));
            tvContent.setPadding(30, 15, 30, 15);
            toast.setView(tvContent);
        }
        tvContent.setText(obj.toString());
        toast.show();
    }

    public static void showLongToast(Object obj) {
        if (null == context) {
            throw new IllegalStateException("init(Context c) must be called before this methord");
        }
        showToast(context, obj, android.widget.Toast.LENGTH_LONG);
    }

    public static void showShortToast(Object obj) {
        if (null == context) {
            throw new IllegalStateException("init(Context c) must be called before this methord");
        }
        showToast(context, obj, android.widget.Toast.LENGTH_SHORT);
    }

    public static void showShortToast(@Nullable Context context, Object obj) {
        showToast(context, obj, android.widget.Toast.LENGTH_SHORT);
    }

    public static void showToast(@Nullable Object obj, int time) {
        if (null == context) {
            throw new IllegalStateException("init(Context c) must be called before this methord");
        }
        showToast(context, obj, time);
    }

}
