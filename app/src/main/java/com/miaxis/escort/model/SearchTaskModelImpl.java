package com.miaxis.escort.model;

import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.local.greenDao.gen.TaskBeanDao;

import java.util.List;

/**
 * Created by 一非 on 2018/4/23.
 */

public class SearchTaskModelImpl implements ISearchTaskModel{

    @Override
    public List<TaskBean> queryTaskByDate(String date) {
        return EscortApp.getInstance().getDaoSession().getTaskBeanDao().queryBuilder()
                .where(TaskBeanDao.Properties.Taskdate.eq(date)).list();
    }
}
