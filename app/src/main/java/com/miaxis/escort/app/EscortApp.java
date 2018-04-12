package com.miaxis.escort.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.miaxis.escort.model.local.greenDao.GreenDaoContext;
import com.miaxis.escort.model.local.greenDao.gen.DaoMaster;
import com.miaxis.escort.model.local.greenDao.gen.DaoSession;

/**
 * Created by 一非 on 2018/4/8.
 */

public class EscortApp extends Application{

    private static EscortApp escortApp;

    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        escortApp = this;
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
        //TODO: 这里的daoSession.clear();是个什么作用
        return daoSession;
    }

}