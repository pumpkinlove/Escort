package com.miaxis.escort.presenter;

import com.miaxis.escort.model.entity.TaskBean;

/**
 * Created by 一非 on 2018/4/24.
 */

public interface ITaskDetailPresenter extends IBasePresenter {
    void deleteTask(TaskBean taskBean);
}
