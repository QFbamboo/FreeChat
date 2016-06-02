package com.bamboo.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import com.bamboo.common.Tag;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bamboo on 16-6-2.
 */
public class LoadPictrue implements Runnable {

    private final String path;
    private final ImageView imageView;
    private Context context;
    private byte[] picByte;

    public LoadPictrue(Context context, String path, ImageView imageView) {
        this.path = path;
        this.imageView = imageView;
        this.context = context;
        new Thread(this).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Tag.SUCCESS) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
                imageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void run() {
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10 * 1000);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = in.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                }
                picByte = bos.toByteArray();
                bos.close();
                in.close();
                connection.disconnect();
                handler.obtainMessage(Tag.SUCCESS).sendToTarget();
            } else {
                handler.obtainMessage(Tag.FAILURE).sendToTarget();
            }
        } catch (Exception e) {
            e.printStackTrace();
            handler.obtainMessage(Tag.FAILURE).sendToTarget();
        }

    }
}
