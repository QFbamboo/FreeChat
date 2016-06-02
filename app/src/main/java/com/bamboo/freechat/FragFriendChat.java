package com.bamboo.freechat;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.bamboo.base.BaseFragment;
import com.bamboo.base.ContentView;
import com.bamboo.base.ViewInject;

/**
 * Created by bamboo on 16-6-2.
 */
@ContentView(R.layout.guide)
public class FragFriendChat extends BaseFragment {

    @ViewInject(R.id.lv_content)
    private ListView listView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
