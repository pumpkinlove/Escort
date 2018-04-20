package com.miaxis.escort.model;

import com.miaxis.escort.model.entity.OpdateBean;
import com.miaxis.escort.model.entity.WorkerBean;

import java.util.List;

/**
 * Created by 一非 on 2018/4/17.
 */

public interface IWorkerManageModel {
    List<WorkerBean> loadWorkerList();
    void saveWorkerList(List<WorkerBean> workerBeanList);
    List<OpdateBean> getWorkerOpdate();
    boolean isExist(WorkerBean workerBean);
}
