package com.bamboo.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bamboo on 16-6-3.
 */
public class Msg implements Serializable {
    private int fromuserid;
    private int flag;
    private String fromUserName;
    private int id;
    private String fromAvatar;
    private long add_time;

    public Msg() {

    }

    public Msg(int fromuserid, int flag, String fromUserName, int id,
               String fromAvatar, long add_time) {

        this.fromuserid = fromuserid;
        this.flag = flag;
        this.fromUserName = fromUserName;
        this.id = id;
        this.fromAvatar = fromAvatar;
        this.add_time = add_time;
    }

    public void setFromuserid(int fromuserid) {
        this.fromuserid = fromuserid;
    }

    public int getFromuserid() {
        return fromuserid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromAvatar(String fromAvatar) {
        this.fromAvatar = fromAvatar;
    }

    public String getFromAvatar() {
        return fromAvatar;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public long getAdd_time() {
        return add_time;
    }

    public static Msg getInstence(JSONObject obj) throws JSONException {

        Msg msg = new Msg(obj.getInt("fromuserid"), obj.getInt("flag"),
                obj.getString("fromusername"), obj.getInt("id"),
                obj.getString("fromavatar"), obj.getLong("add_time"));
        return msg;
    }

    public static List<Msg> getInstence(JSONArray array) throws JSONException {
        List<Msg> list = new ArrayList<Msg>();
        for (int i = 0, k = array.length(); i < k; i++) {
            JSONObject jsonObj = array.getJSONObject(i);
            Msg msg = Msg.getInstence(jsonObj);
            list.add(msg);
        }
        return list;
    }
}
