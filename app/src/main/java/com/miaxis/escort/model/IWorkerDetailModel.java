package com.miaxis.escort.model;

import com.miaxis.escort.model.entity.WorkerBean;

/**
 * Created by 一非 on 2018/4/19.
 */

public interface IWorkerDetailModel {
    boolean isExist(WorkerBean workerBean);
    boolean isDuplicate(String workno);
}
