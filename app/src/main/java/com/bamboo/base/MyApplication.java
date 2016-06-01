package com.bamboo.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMCore;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.wxlib.util.SysUtil;
import com.bamboo.util.IMUtil;
import com.bamboo.util.SPUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.openim.OpenIMAgent;

public class MyApplication extends Application {

    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();

////		DbHelper.init(appContext);
//        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(appContext));
        SysUtil.setApplication(MyApplication.appContext);
//        if (SysUtil.isTCMSServiceProcess(MyApplication.appContext)) {
//            return;
//        }

        if (SysUtil.isMainProcess(MyApplication.appContext)) {
            OpenIMAgent.getInstance(MyApplication.appContext).init();
        }
        SPUtil.getSpUtil(appContext);
        IMUtil.init();

    }
}
