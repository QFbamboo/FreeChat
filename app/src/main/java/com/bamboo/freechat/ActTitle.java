package com.bamboo.freechat;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by bamboo on 16-5-30.
 */
public class ActTitle extends LinearLayout implements View.OnClickListener {

    private TextView back;
    private TextView title_text;

    public ActTitle(Context context) {
        super(context, null);
    }

    public ActTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.msg_title, this, true);
        back = (TextView) findViewById(R.id.back);
        title_text = (TextView) findViewById(R.id.title_text);
        back.setText("<<返回");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }

    public void setTitleName(CharSequence s) {
        title_text.setText(s);
    }

    @Override
    public void onClick(View v) {
        ((Activity) getContext()).finish();
    }
}
