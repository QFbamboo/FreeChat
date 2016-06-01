package com.bamboo.bean;

import org.json.JSONObject;

/**
 * Created by bamboo on 16-6-1.
 */
public class User {

    private int userid;
    private String username;
    private String avatar;

    public User() {

    }

    public User(JSONObject obj) {
        this(obj.optInt("id"), obj.optString("username"), obj.optString("avatar"));
    }

    public User(int userid, String username, String avatar) {
        this.userid = userid;
        this.username = username;
        this.avatar = avatar;
    }

    public int getUserid() {
        return userid;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
