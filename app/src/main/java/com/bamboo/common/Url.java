package com.bamboo.common;

/**
 * Created by bamboo on 16-5-31.
 */
public class Url {
    private static final String BASE_URL = "http://192.168.200.22:8080/bamboo/";
    public static final String GET_USERINFO = BASE_URL + "user/info";
    public static final String REGISTER = BASE_URL + "/user/registe";
    public static final String ADD_FRIEND = BASE_URL + "friend/add";
    public static final String MESSAGE_LIST = BASE_URL + "message/list";
    public static final String FRIEND_REQUEST = BASE_URL + "friend/request";
    public static final String FRIEND_REJECT = BASE_URL + "friend/reject";
    public static final String FRIEND_LIST = BASE_URL + "friend/list";
}
