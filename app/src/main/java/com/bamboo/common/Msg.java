package com.bamboo.common;

import org.json.JSONObject;

import java.io.Serializable;

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
}
