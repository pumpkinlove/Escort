package com.miaxis.escort.model;

import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.model.local.greenDao.gen.WorkerBeanDao;

import java.util.List;

/**
 * Created by tang.yf on 2018/7/27.
 */

public class WorkerBeanModel {

    public static List<WorkerBean> loadWorkerBeanList() {
        return EscortApp.getInstance().getDaoSession().getWorkerBeanDao().queryBuilder()
                .where(WorkerBeanDao.Properties.Status.notEq("未上传")).list();
    }

}
