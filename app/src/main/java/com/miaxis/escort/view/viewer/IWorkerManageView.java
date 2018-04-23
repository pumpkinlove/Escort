package com.miaxis.escort.view.viewer;

import com.miaxis.escort.model.entity.WorkerBean;

import java.util.List;

/**
 * Created by 一非 on 2018/4/17.
 */

public interface IWorkerManageView extends IBaseView{
    void updateDataList(List<WorkerBean> workerBeanList);
    void downWorkerSuccess();
    void downWorkerFailed(String message);
}
