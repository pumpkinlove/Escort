package com.miaxis.escort.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.liulishuo.filedownloader.FileDownloader;
import com.miaxis.escort.model.local.greenDao.GreenDaoContext;
import com.miaxis.escort.model.local.greenDao.gen.DaoMaster;
import com.miaxis.escort.model.local.greenDao.gen.DaoSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 一非 on 2018/4/8.
 */

public class EscortApp extends Application{

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
        DaoMaster.dropAllTables(daoMaster.getDatabase(),true);
        DaoMaster.createAllTables(daoMaster.getDatabase(),true);
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
