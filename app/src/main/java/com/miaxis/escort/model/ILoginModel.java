package com.miaxis.escort.model;

import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.OpdateBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.TaskExeBean;
import com.miaxis.escort.model.entity.WorkerBean;

import java.util.List;

/**
 * Created by 一非 on 2018/4/8.
 */

public interface ILoginModel {
    Config loadConfig();
    void deleteTaskExe(TaskExeBean taskExeBean);
    List<WorkerBean> loadWorker();
    int loadWorkerSize();
}
