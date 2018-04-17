package com.miaxis.escort.model;

import com.miaxis.escort.model.entity.TaskBean;

import java.util.List;

/**
 * Created by 一非 on 2018/4/17.
 */

public interface IMyTaskModel {
    List<TaskBean> loadTask();
    void saveTask(List<TaskBean> taskBeanList);
}
