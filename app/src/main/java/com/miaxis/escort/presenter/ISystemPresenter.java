package com.miaxis.escort.presenter;

import com.miaxis.escort.model.entity.WorkerBean;

import java.net.URL;

/**
 * Created by 一非 on 2018/4/18.
 */

public interface ISystemPresenter extends IBasePresenter{
    void clearAll();
    void updateApp();
    void download(URL url, String path);
    void showCurWorker();
}
