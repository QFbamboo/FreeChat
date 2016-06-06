package com.bamboo.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImgHelper {
    /**
     * 设置图片缓存选项
     * <p>
     * .showImageOnLoading(R.drawable.ic_stub)
     * <p>
     * .showImageOnFail(R.drawable.ic_error)
     */
    private static final DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true).cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    public static void setImage(ImageView iv, String url) {
        ImageLoader.getInstance().displayImage(url, iv, options);
    }

    public static void setImage(ImageView iv, String url,
                                ImageLoadingListener listener) {
        ImageLoader.getInstance().displayImage(url, iv, options, listener);
    }

    public static File getAppDir() {
        File file = new File("/sdcard/bamboo");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public static File getNewImgFile() {
        File f = new File(getAppDir(), SPUtil.getData("username") + System.currentTimeMillis() + ".png");
        try {
            if (!f.exists())
                f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * (int)(Resources.getSystem().getDisplayMetrics().density * dpValue + 0.5f)
     */
    public static int dp_px(float dpValue) {
        return (int) (dpValue * Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px_dp(float pxValue) {
        return (int) (pxValue / Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }
}
