package com.miaxis.escort.model;

import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.TaskBean;

/**
 * Created by 一非 on 2018/4/24.
 */

public class TaskDetailModelImpl implements ITaskDetailModel{
    @Override
    public void deleteTaskLocal(TaskBean taskBean) {
        EscortApp.getInstance().getDaoSession().getTaskBeanDao().delete(taskBean);
    }
}
