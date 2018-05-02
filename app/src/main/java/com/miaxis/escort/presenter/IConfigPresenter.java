package com.miaxis.escort.presenter;

import com.miaxis.escort.model.entity.Config;

/**
 * Created by 一非 on 2018/4/8.
 */

public interface IConfigPresenter extends IBasePresenter{
    void configSave(String ip, String port);
    void configSaveSuccess();
    void configSaveFailed();
    void loadConfig();
    void fetchConfig(Config config);
    void initWorker();
}
