package com.miaxis.escort.model;

import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.OpdateBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.TaskExeBean;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.model.local.greenDao.gen.WorkerBeanDao;
import com.miaxis.escort.presenter.ILoginPresenter;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * Created by 一非 on 2018/4/8.
 */

public class LoginModelImpl implements ILoginModel{

    @Override
    public Config loadConfig() {
        return EscortApp.getInstance().getDaoSession().getConfigDao().load(1L);
    }

    @Override
    public void deleteTaskExe(TaskExeBean taskExeBean) {
        EscortApp.getInstance().getDaoSession().getTaskExeBeanDao().delete(taskExeBean);
    }

    @Override
    public List<WorkerBean> loadWorker() {
        return EscortApp.getInstance().getDaoSession().getWorkerBeanDao().loadAll();
    }

    @Override
    public int loadWorkerSize() {
        return EscortApp.getInstance().getDaoSession().getWorkerBeanDao().loadAll().size();
    }
}
