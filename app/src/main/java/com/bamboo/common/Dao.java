package com.bamboo.common;

import android.os.Handler;

import com.bamboo.base.HttpHelper;
import com.bamboo.bean.User;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by bamboo on 16-6-1.
 */
public class Dao {

    //提供自己的用户名，查询自己或其他人的信息
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
                    handler.obtainMessage(Tag.OTHER).sendToTarget();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                handler.obtainMessage(Tag.OTHER).sendToTarget();

            }
        });
    }

    //提供用户名和密码，用以注册用户
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
                        handler.obtainMessage(Tag.OTHER).sendToTarget();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(Tag.OTHER).sendToTarget();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                handler.obtainMessage(Tag.OTHER).sendToTarget();
            }
        });
    }

    //提供自己的用户名，以及查询到的用名，然后发送添加好友的消息
    public static void addFriend(String selfUsername, String toUsername, final Handler handler) {
        RequestParams rp = new RequestParams();
        rp.put("selfUsername", selfUsername);
        rp.put("toUsername", toUsername);
        HttpHelper.post(Url.ADD_FRIEND, rp, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.getInt("status");
                    String msg = jsonObject.getString("msg");
                    Result result = new Result(status, msg);
                    if (result.status == 1) {
                        handler.obtainMessage(Tag.SUCCESS, result.msg).sendToTarget();
                    } else if (result.status == 0) {
                        handler.obtainMessage(Tag.FAILURE, result.msg).sendToTarget();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(Tag.OTHER).sendToTarget();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                handler.obtainMessage(Tag.OTHER).sendToTarget();
            }
        });
    }

    //若有用户添加此账号为好友，此方法用于接收——其他用户发来的添加好友的信息
    public static void getMessage(String username, final Handler handler) {
        RequestParams rp = new RequestParams();
        rp.put("username", username);
        HttpHelper.get(Url.MESSAGE_LIST, rp, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                //调用Result的getJsonMessage方法
                Result.getJsonMessage(s, new Handler() {
                    @Override
                    public void handleMessage(android.os.Message msg) {
                        if (msg.what == Tag.SUCCESS) {
                            List<Message> list = (List<Message>) msg.obj;
                            handler.obtainMessage(Tag.SUCCESS, list).sendToTarget();
                        } else {
                            handler.obtainMessage(Tag.FAILURE).sendToTarget();
                        }

                    }
                });

            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                handler.obtainMessage(Tag.FAILURE).sendToTarget();
            }
        });

    }

}
