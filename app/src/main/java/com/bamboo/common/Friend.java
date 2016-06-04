package com.bamboo.common;

/**
 * Created by bamboo on 16-6-4.
 */
public class Friend {
    private int id;
    private String avatar;
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
}
