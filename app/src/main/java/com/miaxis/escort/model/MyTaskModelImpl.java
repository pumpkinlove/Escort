package com.miaxis.escort.model;

import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.presenter.IMyTaskPresenter;

import java.util.List;

/**
 * Created by 一非 on 2018/4/17.
 */

public class MyTaskModelImpl implements IMyTaskModel{

    private IMyTaskPresenter myTaskPresenter;

    public MyTaskModelImpl(IMyTaskPresenter myTaskPresenter) {
        this.myTaskPresenter = myTaskPresenter;
    }

    @Override
    public List<TaskBean> loadTask() {
        return EscortApp.getInstance().getDaoSession().getTaskBeanDao().loadAll();
    }

    @Override
    public void saveTask(List<TaskBean> taskBeanList) {
        EscortApp.getInstance().getDaoSession().getTaskBeanDao().insertOrReplaceInTx(taskBeanList);
    }
}
