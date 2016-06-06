package com.bamboo.common;

import android.os.Handler;

import com.alibaba.wxlib.util.http.mime.MultipartEntity;
import com.alibaba.wxlib.util.http.mime.content.FileBody;
import com.bamboo.bean.Friend;
import com.bamboo.bean.Msg;
import com.bamboo.util.HttpHelper;
import com.bamboo.bean.User;
import com.bamboo.util.SPUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
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
    public static void getMessage(final Handler handler) {
        RequestParams rp = new RequestParams();
        rp.put("username", SPUtil.getData("username"));
        HttpHelper.get(Url.MESSAGE_LIST, rp, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        List<Msg> list = Msg.getInstence(array);
                        handler.obtainMessage(Tag.SUCCESS, list).sendToTarget();
                    } else {
                        handler.obtainMessage(Tag.FAILURE).sendToTarget();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(Tag.FAILURE).sendToTarget();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                handler.obtainMessage(Tag.FAILURE).sendToTarget();
            }
        });

    }

    //接受对方加好友的请求
    public static void friendRequest(int msgID, final Handler handler) {
        RequestParams rp = new RequestParams();
        rp.put("username", SPUtil.getData("username"));
        rp.put("msgID", msgID);
        HttpHelper.get(Url.FRIEND_REQUEST, rp, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    Result result = new Result(s);
                    if (result.status == 1) {
                        handler.obtainMessage(Tag.SUCCESS).sendToTarget();
                    } else if (result.status == 0) {
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

    //拒绝对方加好友的请求
    public static void friendReject(int msgID, final Handler handler) {
        RequestParams rp = new RequestParams();
        rp.put("username", SPUtil.getData("username"));
        rp.put("msgID", msgID);
        HttpHelper.get(Url.FRIEND_REJECT, rp, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    Result result = new Result(s);
                    if (result.status == 1) {
                        handler.obtainMessage(Tag.SUCCESS).sendToTarget();
                    } else if (result.status == 0) {
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

    //获取好友列表
    public static void getFriendList(final Handler handler) {
        RequestParams rp = new RequestParams();
        rp.put("username", SPUtil.getData("username"));
        HttpHelper.get(Url.FRIEND_LIST, rp, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        List<Friend> list = Friend.getInstances(array);
                        com.j256.ormlite.dao.Dao<Friend, Integer> dao = Friend.getDao();
                        for (Friend friend : list) {
                            dao.createOrUpdate(friend);
                        }
                        handler.obtainMessage(Tag.SUCCESS, list).sendToTarget();
                    } else {
                        handler.obtainMessage(Tag.FAILURE).sendToTarget();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(Tag.FAILURE).sendToTarget();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                handler.obtainMessage(Tag.FAILURE).sendToTarget();
            }
        });
    }

    //上传用户头像
    public static void upload(File file, final Handler handler) {
     /*   try {
            RequestParams rp = new RequestParams();
            rp.put("file", file);
            HttpHelper.get(Url.UPLOAD_AVATAR, rp, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    handler.obtainMessage(Tag.SUCCESS).sendToTarget();
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    handler.obtainMessage(Tag.FAILURE).sendToTarget();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
            handler.obtainMessage(Tag.FAILURE).sendToTarget();
        }*/
    }

    public static void uploadAvatar(File f, final Handler handler) {
        final AsyncHttpClient httpClient = new AsyncHttpClient();
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("file[]", new FileBody(f));
        httpClient.post(null, Url.UPLOAD_AVATAR, entity, "multipart/form-data", new TextHttpResponseHandler() {

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

            }
        });
    }

    //更新用户头像信息
    public static void upAvatarData(String avatar, final Handler handler) {
        RequestParams rp = new RequestParams();
        rp.put("username", SPUtil.getData("username"));
        rp.put("avatar", avatar);
        HttpHelper.get(Url.UPAVATAR_DATA, rp, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        handler.obtainMessage(Tag.SUCCESS).sendToTarget();
                    } else {
                        handler.obtainMessage(Tag.FAILURE).sendToTarget();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(Tag.FAILURE).sendToTarget();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                handler.obtainMessage(Tag.FAILURE).sendToTarget();
            }
        });
    }

    //删除好友操作
    public static void deleteFriend(String toUsername, final Handler handler) {
        RequestParams rp = new RequestParams();
        rp.put("selfUsername", SPUtil.getData("username"));
        rp.put("toUsername", toUsername);
        HttpHelper.get(Url.FRIEND_DELETE, rp, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        handler.obtainMessage(Tag.SUCCESS).sendToTarget();
                    } else {
                        handler.obtainMessage(Tag.FAILURE).sendToTarget();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(Tag.FAILURE).sendToTarget();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                handler.obtainMessage(Tag.FAILURE).sendToTarget();
            }
        });
    }

    //删除消息操作
    public static void deleteMessage(String msgID, final Handler handler) {
        RequestParams rp = new RequestParams();
        rp.put("username", SPUtil.getData("username"));
        rp.put("msgID", msgID);
        HttpHelper.get(Url.MESSAGE_DELETE, rp, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject jsonObjec = new JSONObject(s);
                    int status = jsonObjec.getInt("status");
                    if (status == 1) {
                        handler.obtainMessage(Tag.SUCCESS).sendToTarget();
                    } else {
                        handler.obtainMessage(Tag.FAILURE).sendToTarget();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(Tag.FAILURE).sendToTarget();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                handler.obtainMessage(Tag.FAILURE).sendToTarget();
            }
        });
    }
}
