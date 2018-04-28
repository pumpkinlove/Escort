package com.miaxis.escort.view.viewer;

import com.miaxis.escort.model.entity.Config;

/**
 * Created by 一非 on 2018/4/8.
 */

public interface IConfigView extends IBaseView{
    void configSaveSuccess();
    void configSaveFailed();
    void fetchConfig(Config config);
    void setProgressDialogMessage(String message);
    void initWorker();
}
