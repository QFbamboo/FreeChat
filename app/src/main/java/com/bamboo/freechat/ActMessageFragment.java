package com.bamboo.freechat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bamboo.base.BaseFragment;
import com.bamboo.base.ContentView;
import com.bamboo.base.MyAdapter;
import com.bamboo.base.ViewInject;

/**
 * Created by bamboo on 16-5-31.
 */
@ContentView(R.layout.guide)
public class ActMessageFragment extends BaseFragment {

    @ViewInject(R.id.lv_content)
    private ListView listView;

    public ActMessageFragment newIntence(String[] arr) {
        ActMessageFragment newFragment = new ActMessageFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("msg", arr);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public Context getContext() {
        return super.getContext();
//        return context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle msg = getArguments();
        String[] arr = msg.getStringArray("msg");
        lvSetAdapter(arr);
    }


    public void lvSetAdapter(String[] arr) {
//        List<String> list = new ArrayList<String>();
//        getActivity().getApplication();

        MyAdapter<String> adapter = new MyAdapter<String>(getContext(), arr) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = getHolder(getContext(),
                        convertView, parent, R.layout.msg_content, position);
                TextView textView = holder.getView(R.id.msgText);
                String line = getItem(position);
                textView.setText(line);
                return holder.getConvertView();
            }
        };
        listView.setAdapter(adapter);
    }
}
