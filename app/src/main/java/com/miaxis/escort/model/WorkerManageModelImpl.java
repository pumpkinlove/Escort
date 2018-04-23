package com.miaxis.escort.model;

import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.OpdateBean;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.model.local.greenDao.gen.WorkerBeanDao;
import com.miaxis.escort.presenter.IWorkerManagePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 一非 on 2018/4/17.
 */

public class WorkerManageModelImpl implements IWorkerManageModel{

    @Override
    public List<WorkerBean> loadWorkerList() {
        return EscortApp.getInstance().getDaoSession().getWorkerBeanDao().loadAll();
    }

    @Override
    public void saveWorkerList(List<WorkerBean> workerBeanList) {
        List<WorkerBean> list = EscortApp.getInstance().getDaoSession().getWorkerBeanDao().queryBuilder()
                .where(WorkerBeanDao.Properties.Status.notEq("未上传")).list();
        for (WorkerBean workerBean : list) {
            EscortApp.getInstance().getDaoSession().getWorkerBeanDao().delete(workerBean);
        }
        List<WorkerBean> workerBeans = new ArrayList<>();
        for (WorkerBean workerBean : workerBeanList) {
            workerBean.setStatus("已上传");
            workerBeans.add(workerBean);
        }
        EscortApp.getInstance().getDaoSession().getWorkerBeanDao().insertOrReplaceInTx(workerBeanList);
    }

    @Override
    public List<OpdateBean> getWorkerOpdate() {
        List<WorkerBean> workerBeanList = EscortApp.getInstance().getDaoSession().getWorkerBeanDao().loadAll();
        List<OpdateBean> opdateBeanList = new ArrayList<>();
        for (WorkerBean workerBean : workerBeanList) {
            if (!"未上传".equals(workerBean.getStatus())) {
                opdateBeanList.add(new OpdateBean(workerBean.getId(), workerBean.getOpdate()));
            }
        }
        return opdateBeanList;
    }

    @Override
    public boolean isExist(WorkerBean workerBean) {
        WorkerBean data = EscortApp.getInstance().getDaoSession().getWorkerBeanDao().queryBuilder()
                .where(WorkerBeanDao.Properties.Workno.eq(workerBean.getWorkno())).unique();
        if (data == null) {
            return false;
        }
        return true;
    }

}
