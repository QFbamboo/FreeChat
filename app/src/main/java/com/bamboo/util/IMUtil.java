package com.bamboo.util;

import android.app.Activity;
import android.os.Handler;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.contact.IYWContactService;
import com.alibaba.mobileim.contact.YWContactManager;
import com.alibaba.mobileim.conversation.IYWConversationListener;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.wxlib.util.SysUtil;
import com.bamboo.base.MyApplication;
import com.bamboo.common.Tag;
import com.umeng.openim.OpenIMAgent;

/**
 * Created by caojiang on 2/24/2016.
 */
public class IMUtil {
    public static final String ImAppKey = "23377576";
    private static boolean isaddlistener = false;
    private static YWIMKit mIMKit;
    private static IYWLoginService mLoginService;
    private static IYWContactService mContactService;
    private static IYWConversationService mConversationService;


//    public static final void init(Context con) {
////        AdviceBinder.bindAdvice(PointCutEnum.CHATTING_FRAGMENT_UI_POINTCUT, ChatCustomAdvice.class);
//        OpenIMAgent.getInstance(con).init();
//    }

    public static final void init() {
        mIMKit = YWAPI.getIMKitInstance();
        if (mIMKit != null) {
            mLoginService = mIMKit.getLoginService();
            mContactService = mIMKit.getContactService();
            mConversationService = mIMKit.getConversationService();
//            mContactService.setContactProfileCallback(callback);
//            AdviceBinder.bindAdvice(PointCutEnum.CONVERSATION_FRAGMENT_UI_POINTCUT, ConversationListUICustomSample.class);
        }
    }

//    private static final IYWContactProfileCallback callback = new IYWContactProfileCallback() {
//        private Friends f;
//
//        @Override
//        public IYWContact onFetchContactInfo(final String username) {
//            return Conf.getUserName().equals(username) ? getSelfContact() : (f = Friends.getFriendByName(username)) == null ? mContactManager.getWXIMContact(username) : new IMContact(f);
//        }
//
//        @Override
//        public Intent onShowProfileActivity(String s) {
//            return null;
//        }
//    };
//
//    public static final IYWContact getSelfContact() {
//        return new IMContact(Config.getUserName(), Config.getImageUrl(Config.getAvator()), Config.getNikename());
//    }

    public static final IYWConversationService getCs() {
        if (mConversationService == null) {
            init();
        }
        return mConversationService;
    }

    public static final void AddIYWConversationServiceListener(IYWConversationListener listener) {
        if (mConversationService == null) {
            init();
        }
        if (mConversationService != null && !isaddlistener) {
            isaddlistener = true;
            mConversationService.addConversationListener(listener);
        }

    }

    public static final void RemoveIYWConversationServiceListener(IYWConversationListener listener) {
        if (mConversationService == null) {
            init();
        }
        if (mConversationService != null && isaddlistener) {
            isaddlistener = false;
            mConversationService.removeConversationListener(listener);
        }
    }

    public static final void login(String username, String password, final Handler h) {
        if (mLoginService == null) {
            init();
        }
        mLoginService.login(YWLoginParam.createLoginParam(username, password), new IWxCallback() {
            @Override
            public void onSuccess(Object... objects) {
                h.obtainMessage(Tag.SUCCESS).sendToTarget();
            }

            @Override
            public void onError(int i, String s) {
                h.obtainMessage(Tag.FAILURE).sendToTarget();
            }

            @Override
            public void onProgress(int i) {
            }
        });
    }

    public static final void loginOut(final Handler handler) {
        mLoginService.logout(new IWxCallback() {
            @Override
            public void onSuccess(Object... objects) {
                handler.obtainMessage(Tag.OUT_SUCCESS).sendToTarget();
            }

            @Override
            public void onError(int i, String s) {
                handler.obtainMessage(Tag.OUT_FAILURE).sendToTarget();
            }

            @Override
            public void onProgress(int i) {

            }
        });

    }

    public static final void startChatAct(Activity act, String username) {
        act.startActivity(mIMKit.getChattingActivityIntent(username));
    }

    public static IYWLoginService getLoginService() {
        return mLoginService;
    }

    public static YWIMKit getIMKit() {
        if (mIMKit == null) {
            init();
        }
        return mIMKit;
    }
}
