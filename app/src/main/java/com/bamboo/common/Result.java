package com.bamboo.common;

import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bamboo on 16-6-1.
 */
public class Result {
    public final int status;
    public final String msg;
    public final Object data;


    public Result(int status, String msg) throws Exception {
        this.status = status;
        this.msg = msg;
        this.data = null;
    }


    public Result(String s) throws Exception {
        JSONObject obj = new JSONObject(s);
        this.status = obj.getInt("status");
        this.msg = obj.getString("msg");
        this.data = obj.get("data");
    }

    public String getMsg() {
        return msg;
    }

    public static void getJsonMessage(String jsonText, final Handler handler) {
        List<Msg> list = new ArrayList<Msg>();
        try {
            JSONObject jsonObject = new JSONObject(jsonText);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(j);
                    int fromuserid = jsonObj.getInt("fromuserid");
                    int flag = jsonObj.getInt("flag");
                    String fromusername = jsonObj.getString("fromusername");
                    int id = jsonObj.getInt("id");
                    String fromavatar = jsonObj.getString("fromavatar");
                    long add_time = jsonObj.getLong("add_time");
                    Msg msg = new Msg(fromuserid, flag, fromusername, id,
                            fromavatar, add_time);
                    list.add(msg);
                    handler.obtainMessage(Tag.SUCCESS, list).sendToTarget();
                }
            } else {
                handler.obtainMessage(Tag.FAILURE).sendToTarget();
            }
        } catch (Exception e) {
            e.printStackTrace();
            handler.obtainMessage(Tag.FAILURE).sendToTarget();
        }
    }

    public static void getJsonFriendlist(String s, final Handler handler) {
        List<Friend> list = new ArrayList<Friend>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    int id = jsonObj.getInt("id");
                    String avatar = jsonObj.getString("avatar");
                    String username = jsonObj.getString("username");
                    Friend friend = new Friend(id, avatar, username);
                    list.add(friend);
                    handler.obtainMessage(Tag.SUCCESS, list).sendToTarget();
                }
            } else {
                handler.obtainMessage(Tag.FAILURE).sendToTarget();
            }
        } catch (Exception e) {
            e.printStackTrace();
            handler.obtainMessage(Tag.FAILURE).sendToTarget();
        }
    }

    @Override
    public String toString() {
        return msg;
    }
}
