package com.miaxis.escort.model;

import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.model.local.greenDao.gen.WorkerBeanDao;

/**
 * Created by 一非 on 2018/4/19.
 */

public class WorkerDetailModel implements IWorkerDetailModel{

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
