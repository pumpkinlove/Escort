package com.miaxis.escort.view.viewer;

import com.miaxis.escort.model.entity.TaskBean;

import java.util.List;

/**
 * Created by 一非 on 2018/4/17.
 */

public interface IMyTaskView extends IBaseView{
    void setDialogMessage(String message);
    void downTaskSuccess();
    void downTaskFailed();
    void updateData(List<TaskBean> taskBeanList);
}
