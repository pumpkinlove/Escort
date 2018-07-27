package com.miaxis.escort.presenter;

import android.app.Activity;

/**
 * Created by 一非 on 2018/4/8.
 */

public interface ILoginPresenter extends IBasePresenter{
    void getPermissions(Activity activity);
    void initAppData();
    void resumeTaskExe();
    void login();
    void loadConfig();
    int loadWorkerSize();
    void loadBank();
}
