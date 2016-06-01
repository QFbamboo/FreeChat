package com.bamboo.common;

import android.os.Handler;
import android.widget.Toast;

import com.bamboo.base.HttpHelper;
import com.bamboo.bean.User;
import com.bamboo.util.SPUtil;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by bamboo on 16-6-1.
 */
public class Dao {
    public static void getUserInfo(String selfUsername, String toUsername, final Handler handler) {
        RequestParams rp = new RequestParams();
        rp.put("selfUsername", selfUsername);
        rp.put("toUsername", toUsername);

        HttpHelper.get(Url.GET_USERINFO, rp, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                handler.obtainMessage(Tag.FAILURE).sendToTarget();

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    Result result = new Result(s);
                    if (result.status == 1) {
                        User user=new User((JSONObject)result.data);
                        SPUtil.setDate("","");
                        handler.obtainMessage(Tag.SUCCESS).sendToTarget();
                    } else {
                        handler.obtainMessage(Tag.FAILURE).sendToTarget();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(Tag.FAILURE).sendToTarget();
                }
            }
        });
    }


}
