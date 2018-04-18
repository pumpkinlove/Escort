package com.miaxis.escort.model;

import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.OpdateBean;
import com.miaxis.escort.model.entity.TaskBean;

import java.util.List;

/**
 * Created by 一非 on 2018/4/17.
 */

public interface IMyTaskModel {
    List<TaskBean> loadTask();
    void saveTask(List<TaskBean> taskBeanList);
    List<OpdateBean> getBoxOpdateByTaskDate(String taskdate);
    void saveBox(List<BoxBean> boxBeanList);
    List<OpdateBean> getEscortOpdate(String taskdate);
    void saveEscort(List<EscortBean> escortBeanList);
    List<OpdateBean> getBoxOpdate();
}
