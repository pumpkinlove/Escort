package com.miaxis.escort.app;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.liulishuo.filedownloader.FileDownloader;
import com.miaxis.escort.ScreenListener;
import com.miaxis.escort.model.local.greenDao.GreenDaoContext;
import com.miaxis.escort.model.local.greenDao.gen.DaoMaster;
import com.miaxis.escort.model.local.greenDao.gen.DaoSession;
import com.miaxis.escort.view.activity.LoginActivity;
import com.miaxis.escort.view.fragment.SystemFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 一非 on 2018/4/8.
 */

public class EscortApp extends Application {

    private static EscortApp escortApp;

    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Map<String, Object> map;

    @Override
    public void onCreate() {
        super.onCreate();
        escortApp = this;
        map = new HashMap<>();
        FileDownloader.setup(this);
        ScreenListener listener = new ScreenListener(this);
        listener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                Log.e("===", "onScreenOn");
            }

            @Override
            public void onScreenOff() {
                Log.e("===", "onScreenOff");
            }

            @Override
            public void onUserPresent() {
                Log.e("===", "onUserPresent");
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    public static EscortApp getInstance() {
        return escortApp;
    }

    /**
     * 由于运行时权限依赖于LoginActivity，数据库初始化在拿到权限后进行
     */
    public void initDbHelp() {
        helper = new DaoMaster.DevOpenHelper(new GreenDaoContext(this), "Escort.db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        daoSession.clear();
        return daoSession;
    }

    public void clearAndRebuildDatabase() {
        DaoMaster.dropAllTables(daoMaster.getDatabase(), true);
        DaoMaster.createAllTables(daoMaster.getDatabase(), true);
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public Object get(String key) {
        return map.get(key);
    }

    public void clearMap() {
        map.clear();
    }

}
