package com.miaxis.escort.model;

import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.TaskBoxBean;
import com.miaxis.escort.model.entity.TaskExeBean;
import com.miaxis.escort.model.local.greenDao.gen.TaskBeanDao;
import com.miaxis.escort.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 一非 on 2018/4/20.
 */

public class VerifyBoxModelImpl implements IVerifyBoxModel{

    @Override
    public List<BoxBean> loadBoxListByTask(TaskBean taskBean) {
        List<BoxBean> boxs = new ArrayList<>();
        List<BoxBean> boxBeanList = EscortApp.getInstance().getDaoSession().getBoxBeanDao().loadAll();
        for (TaskBoxBean taskBoxBean : taskBean.getBoxList()) {
            for (BoxBean boxBean : boxBeanList) {
                if (taskBoxBean.getBoxcode().equals(boxBean.getBoxcode())) {
                    boxs.add(boxBean);
                }
            }
        }
        return boxs;
    }

    @Override
    public void updateTaskStatus(TaskExeBean taskExeBean) {
        TaskBean taskBean = EscortApp.getInstance().getDaoSession().getTaskBeanDao().queryBuilder()
                .where(TaskBeanDao.Properties.Taskcode.eq(taskExeBean.getTaskno())).unique();
        taskBean.setStatus("4");
        taskBean.setExetime(taskExeBean.getTasktime().substring(11));
        EscortApp.getInstance().getDaoSession().insertOrReplace(taskBean);
    }

    @Override
    public void saveLocal(TaskExeBean taskExeBean) {
        EscortApp.getInstance().getDaoSession().getTaskExeBeanDao().insertOrReplace(taskExeBean);
    }
}
