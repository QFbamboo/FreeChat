package com.bamboo.common;

import org.json.JSONObject;

/**
 * Created by bamboo on 16-6-1.
 */
public class Result {
    public final int status;
    public final String msg;
    public final Object data;


    public Result(int status, String msg) {
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


    @Override
    public String toString() {
        return msg;
    }
}
