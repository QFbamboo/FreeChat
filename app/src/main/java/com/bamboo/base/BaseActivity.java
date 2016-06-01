package com.bamboo.base;

import java.lang.reflect.Field;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

public class BaseActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadView();
    }

    private void loadView() {
        try {
            setContentView(getClass().getAnnotation(ContentView.class).value());
            for (Field fs : getClass().getDeclaredFields()) {
                if (fs.isAnnotationPresent(ViewInject.class)) {
                    fs.setAccessible(true);
                    fs.set(this, findViewById(fs
                            .getAnnotation(ViewInject.class).value()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T f(int res) {
        return (T) findViewById(res);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T f(int res, View v) {
        return (T) v.findViewById(res);
    }

    /**
     * @return isEmpty or isNull
     */
    public static final boolean em(String str) {
        return null == str || str.isEmpty();
    }

    /**
     * @return isEmpty or isNull
     */
    public static final boolean em(java.util.Collection<?> c) {
        return null == c || c.isEmpty();
    }

    /**
     * @return isEmpty or isNull
     */
    public static final boolean em(java.util.Map<?, ?> m) {
        return null == m || m.isEmpty();
    }

    /**
     * @return equals
     */
    public static final boolean eq(Object obj1, Object obj2) {
        return obj1 == obj2 || obj1.equals(obj2);
    }

    /**
     * @return isNull
     */
    public static final boolean isN(Object obj) {
        return obj == null;
    }

    @Override
    public void onClick(View arg0) {
    }

}
