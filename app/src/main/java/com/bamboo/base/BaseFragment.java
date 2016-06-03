package com.bamboo.base;

/**
 * Created by bamboo on 16-5-29.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.lang.reflect.Field;


public class BaseFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener {
    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loadView(inflater, container);
        return rootView;
    }

    public void loadView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        if (getClass().isAnnotationPresent(ContentView.class)) {
            rootView = layoutInflater.inflate(getClass().getAnnotation(ContentView.class).value(), viewGroup, false);
            Field[] fields = getClass().getDeclaredFields();
            try {
                for (Field field : fields) {
                    if (field.isAnnotationPresent(ViewInject.class)) {
                        field.setAccessible(true);
                        field.set(this, rootView.findViewById(field.getAnnotation(ViewInject.class).value()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(BaseActivity.class.getName(), e.toString());
            }
        }
    }

    public <T extends View> T f(int id, View v) {
        return (T) v.findViewById(id);
    }

    @Override
    public void onClick(View v) {
    }

    public void sg(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }

    public void sv(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
}