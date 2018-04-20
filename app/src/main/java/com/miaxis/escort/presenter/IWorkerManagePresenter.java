package com.miaxis.escort.presenter;

/**
 * Created by 一非 on 2018/4/17.
 */

public interface IWorkerManagePresenter extends IBasePresenter{
    void downWorkerList();
    void loadWorkerList();
    boolean isSelf();
}
