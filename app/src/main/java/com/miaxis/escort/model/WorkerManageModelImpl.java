package com.miaxis.escort.model;

import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.OpdateBean;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.presenter.IWorkerManagePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 一非 on 2018/4/17.
 */

public class WorkerManageModelImpl implements IWorkerManageModel{

    private IWorkerManagePresenter workerManagePresenter;

    public WorkerManageModelImpl(IWorkerManagePresenter workerManagePresenter) {
        this.workerManagePresenter = workerManagePresenter;
    }

    @Override
    public List<WorkerBean> loadWorkerList() {
        return EscortApp.getInstance().getDaoSession().getWorkerBeanDao().loadAll();
    }

    @Override
    public void saveWorkerList(List<WorkerBean> workerBeanList) {
        EscortApp.getInstance().getDaoSession().getWorkerBeanDao().insertOrReplaceInTx(workerBeanList);
    }

    @Override
    public List<OpdateBean> getWorkerOpdate() {
        List<WorkerBean> workerBeanList = EscortApp.getInstance().getDaoSession().getWorkerBeanDao().loadAll();
        List<OpdateBean> opdateBeanList = new ArrayList<>();
        for (WorkerBean workerBean : workerBeanList) {
            opdateBeanList.add(new OpdateBean(workerBean.getId(), workerBean.getOpdate()));
        }
        return opdateBeanList;
    }
}
