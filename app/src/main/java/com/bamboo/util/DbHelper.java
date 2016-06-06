package com.bamboo.util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bamboo.bean.Friend;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by caojiang on 5/20/2016.
 */

public class DbHelper extends OrmLiteSqliteOpenHelper {
    // /data/data/[package]/databases/*.db
    private static final String DATABASE_NAME = "userfriendlist.db";

    private Map<Class, Dao> daos = new HashMap<Class, Dao>();

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, Friend.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Friend.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static DbHelper instance;

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DbHelper getHelper(Context context) {
        if (instance == null) {
            synchronized (DbHelper.class) {
                if (instance == null)
                    instance = new DbHelper(context.getApplicationContext());
            }
        }
        return instance;
    }

    public static synchronized void init(Context context) {
        if (instance == null) {
            synchronized (DbHelper.class) {
                if (instance == null)
                    instance = new DbHelper(context.getApplicationContext());
            }
        }
    }

    public static DbHelper getHelper() {
        return instance;
    }

    public synchronized Dao getDao(Class clazz) {
        Dao dao = daos.get(clazz);
        if (dao == null) {
            try {
                dao = super.getDao(clazz);
            } catch (SQLException e) {
            }
            daos.put(clazz, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        daos.clear();
    }

}
