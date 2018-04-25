package com.miaxis.escort.model;

import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.TaskExeBean;

import java.util.List;

/**
 * Created by 一非 on 2018/4/20.
 */

public interface IVerifyBoxModel {
    List<BoxBean> loadBoxListByTask(TaskBean taskBean);
    void updateTaskStatus(TaskExeBean taskExeBean);
    void saveLocal(TaskExeBean taskExeBean);
}
