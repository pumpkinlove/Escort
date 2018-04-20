package com.miaxis.escort.presenter;

import com.miaxis.escort.model.entity.WorkerBean;

/**
 * Created by 一非 on 2018/4/19.
 */

public interface IWorkerDetailPresenter extends IBasePresenter{
    void addWorker(WorkerBean workerBean);
    void deleteWorker(String workercode);
    boolean isSelf();
}
