package com.bamboo.bean;

import com.bamboo.util.DbHelper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.dao.Dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bamboo on 16-6-4.
 */
@DatabaseTable(tableName = "friendList")
public class Friend implements Serializable {
    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String avatar;
    @DatabaseField
    private String username;

    public Friend() {

    }

    public Friend(int id, String avatar, String username) {
        this.id = id;
        this.avatar = avatar;
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public static Dao<Friend, Integer> getDao() {
        return DbHelper.getHelper().getDao(Friend.class);
    }

    public static List<Friend> getInstances() {
        try {
            return Friend.getDao().queryForAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }


    public static Friend getInstance(JSONObject obj) throws JSONException {
        Friend friend = new Friend(obj.getInt("id"), obj.getString("avatar"), obj.getString("username"));
        return friend;
    }

    public static List<Friend> getInstances(JSONArray array) throws JSONException {
        List<Friend> list = new ArrayList<Friend>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObj = array.getJSONObject(i);
            Friend friend = Friend.getInstance(jsonObj);
            list.add(friend);
        }
        return list;
    }
}
