package com.bamboo.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bamboo.freechat.R;

/**
 * Created by caojiang on 4/29/2016.
 */
public class DialogEditView {
    public final LinearLayout mLayout;
    public final EditText edit;
    public final Button leftbtn, rightbtn;

    private DialogEditView(Context context) {
        mLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_input_text, null);
        edit = (EditText) mLayout.findViewById(R.id.dialog_edit);
        leftbtn = (Button) mLayout.findViewById(R.id.dialog_left);
        rightbtn = (Button) mLayout.findViewById(R.id.dialog_right);
    }

    public String getEditString() {
        return edit.getText().toString().trim();
    }

    public void setEditString(CharSequence str) {
        edit.setText(str);
    }

    public static DialogEditView showDialog(Activity context, String message, String leftMsg, final OnClickListener leftClick, String rightMsg, final OnClickListener rightClick) {
        final DialogEditView dialogView = new DialogEditView(context);
        dialogView.edit.setHint(message);
        dialogView.leftbtn.setText(leftMsg);
        dialogView.rightbtn.setText(rightMsg);

        final Dialog dialog = new Dialog(context);
        Window window = dialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.setContentView(dialogView.mLayout);
        dialog.show();
        dialogView.leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftClick != null) {
                    leftClick.onClick(dialogView);
                }
                dialog.dismiss();
            }
        });
        dialogView.rightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightClick != null) {
                    rightClick.onClick(dialogView);
                }
                dialog.dismiss();
            }
        });
        return dialogView;
    }

    public static DialogEditView showDialog(Activity context, String message, OnClickListener rightclick) {
        return showDialog(context, message, "取消", null, "确定", rightclick);
    }

    public interface OnClickListener {
        void onClick(DialogEditView dialogView);
    }
}
