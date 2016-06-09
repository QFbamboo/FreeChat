package com.bamboo.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bamboo.freechat.R;

/**
 * Created by bamboo on 16-5-30.
 */
public class TitleView extends LinearLayout implements View.OnClickListener {

    private final TextView back;
    private final TextView title_text;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.msg_title, this, true);
        back = (TextView) findViewById(R.id.back);
        title_text = (TextView) findViewById(R.id.title_text);
        back.setText("< 返回");
        back.setOnClickListener(this);
    }

    public void setTitleName(CharSequence s) {
        title_text.setText(s);
    }

    public void setGoneBack() {
        back.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        ((Activity) getContext()).finish();
    }
}
