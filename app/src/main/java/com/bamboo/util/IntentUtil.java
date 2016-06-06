package com.bamboo.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;


public class IntentUtil {

    public static Intent getCaptureIntent(File srcFile, File targetFile) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(srcFile), "image/*");
        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
        //intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
        //intent.putExtra("aspectY", 1);// x:y=1:2
        intent.putExtra("output", Uri.fromFile(targetFile));
        intent.putExtra("outputFormat", "JPEG");//返回格式
        return intent;
    }

    /**
     * @return 从图库选择图片的Intent
     */
    public static Intent getPicIntent() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        return intent;
    }
    //获得截取头像的intent


    public static final Intent getAvaIntent(File srcFile, File targetFile) {
        return IntentUtil.getCaptureIntent(srcFile, targetFile).putExtra("aspectX", 1).putExtra("aspectY", 1).putExtra("outputX", 100).putExtra("outputY", 100);
    }


    public static String getPicPath(Context context, Intent intent) {
        Uri uri = intent.getData();
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(index);
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

}
