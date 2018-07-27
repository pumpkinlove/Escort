package com.miaxis.escort.presenter;

import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.WorkerBean;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tang.yf on 2018/7/27.
 */

public interface IExecuteTaskDialogPresenter extends IBasePresenter {
    void init(int workerNum, List<WorkerBean> workerBeanList, int escortNum, List<EscortBean> escortBeanList);
    void startVerify();
    void startVerifyEscort();
    void closeThread();
    ArrayList<WorkerBean> getWorkerCache();
    ArrayList<EscortBean> getEscortCache();
}
