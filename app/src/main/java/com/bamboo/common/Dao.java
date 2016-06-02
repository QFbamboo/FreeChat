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
    //
    public static void getUserInfo(String selfUsername, String toUsername, final Handler handler) {
        RequestParams rp = new RequestParams();
        rp.put("selfUsername", selfUsername);
        rp.put("toUsername", toUsername);

        HttpHelper.get(Url.GET_USERINFO, rp, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    Result result = new Result(s);
                    if (result.status == 1) {//判断返回的状态码
                        User user = new User((JSONObject) result.data);//把返回的data数据给User类
                        handler.obtainMessage(Tag.SUCCESS, user).sendToTarget();
                    } else {
                        handler.obtainMessage(Tag.FAILURE).sendToTarget();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(Tag.JUDGE).sendToTarget();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                handler.obtainMessage(Tag.JUDGE).sendToTarget();

            }
        });
    }

    public static void userRegister(String username, String password, final Handler handler) {
        RequestParams rp = new RequestParams();
        rp.put("username", username);
        rp.put("password", password);

        HttpHelper.post(Url.REGISTER, rp, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.getInt("status");
                    String msg = jsonObject.getString("msg");
                    Result result = new Result(status, msg);
                    if (result.status == 1) {
                        handler.obtainMessage(Tag.SUCCESS).sendToTarget();
                    } else if (result.status == 0) {
                        handler.obtainMessage(Tag.FAILURE).sendToTarget();
                    } else {
                        handler.obtainMessage(Tag.JUDGE).sendToTarget();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(Tag.JUDGE).sendToTarget();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                handler.obtainMessage(Tag.JUDGE).sendToTarget();
            }
        });
    }


}
