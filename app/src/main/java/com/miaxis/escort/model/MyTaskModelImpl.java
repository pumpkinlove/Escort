package com.miaxis.escort.model;

import android.database.Cursor;

import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.OpdateBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.TaskBoxBean;
import com.miaxis.escort.model.entity.TaskEscortBean;
import com.miaxis.escort.model.local.greenDao.gen.BoxBeanDao;
import com.miaxis.escort.model.local.greenDao.gen.EscortBeanDao;
import com.miaxis.escort.model.local.greenDao.gen.TaskBeanDao;
import com.miaxis.escort.model.local.greenDao.gen.TaskBoxBeanDao;
import com.miaxis.escort.model.local.greenDao.gen.TaskEscortBeanDao;
import com.miaxis.escort.presenter.IMyTaskPresenter;
import com.miaxis.escort.util.DateUtil;
import com.miaxis.escort.util.StaticVariable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 一非 on 2018/4/17.
 */

public class MyTaskModelImpl implements IMyTaskModel{

    @Override
    public List<TaskBean> loadTask() {
        List<TaskBean> taskBeanList = EscortApp.getInstance().getDaoSession().getTaskBeanDao().queryBuilder()
                .where(TaskBeanDao.Properties.Taskdate.eq(DateUtil.getToday()))
                .where(TaskBeanDao.Properties.Status.notEq("4"))
                .where(TaskBeanDao.Properties.Status.notEq("1")).list();
        Config config = (Config) EscortApp.getInstance().get(StaticVariable.CONFIG);
        for (Iterator<TaskBean> it = taskBeanList.iterator(); it.hasNext();){
            TaskBean taskBean = it.next();
            try {
                if("1".equals(taskBean.getTasklevel())){
                    it.remove();
                } else if ("1".equals(taskBean.getTasktype())) {

                } else if ("2".equals(taskBean.getTasktype())) {
                    if ("3".equals(taskBean.getStatus())) {
                        it.remove();
                    }
                } else if ("3".equals(taskBean.getTasktype())) {
                    if (!config.getOrgCode().equals(taskBean.getTaskcode().substring(0,10))) {
                        if ("3".equals(taskBean.getStatus())) {
                            it.remove();
                        }
                    }
                } else if ("4".equals(taskBean.getTasktype())) {
                    if (config.getOrgCode().equals(taskBean.getTaskcode().substring(0,10))) {
                        if ("3".equals(taskBean.getStatus())) {
                            it.remove();
                        }
                    }
                }

            } catch (Exception e) {
            }
        }
        return taskBeanList;
    }

    @Override
    public void saveTask(List<TaskBean> taskBeanList) {
        TaskBeanDao taskBeanDao = EscortApp.getInstance().getDaoSession().getTaskBeanDao();
        TaskBoxBeanDao taskBoxBeanDao = EscortApp.getInstance().getDaoSession().getTaskBoxBeanDao();
        TaskEscortBeanDao taskEscortBeanDao = EscortApp.getInstance().getDaoSession().getTaskEscortBeanDao();
        for (TaskBean task: taskBeanList){
            //task.setDeptno(deptno);
            task.setCreateuser("admin");
            TaskBean oldTask = taskBeanDao.queryBuilder()
                    .where(TaskBeanDao.Properties.Taskcode.eq(task.getTaskcode())).unique();
            if (oldTask != null){
                taskBeanDao.delete(task);
                List<TaskBoxBean> mTaskBoxList = taskBoxBeanDao.queryBuilder()
                        .where(TaskBoxBeanDao.Properties.Taskid.eq(oldTask.getId())).list();
                taskBoxBeanDao.deleteInTx(mTaskBoxList);
                List<TaskEscortBean> mTaskEscortList = taskEscortBeanDao.queryBuilder()
                        .where(TaskEscortBeanDao.Properties.Taskid.eq(oldTask.getId())).list();
                taskEscortBeanDao.deleteInTx(mTaskEscortList);
            }
            if (!task.getStatus().equals("5")){
                taskBeanDao.insert(task);
            }
        }
        for (TaskBean taskBean : taskBeanList) {
            taskBoxBeanDao.insertOrReplaceInTx(taskBean.getBoxList());
            taskEscortBeanDao.insertOrReplaceInTx(taskBean.getEscortList());
        }
    }

    @Override
    public List<OpdateBean> getBoxOpdateByTaskDate(String taskdate) {
        // 生成sql语句
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT BOXCODE FROM TASK_BOX_BEAN ");
        sql.append("WHERE BOXCODE NOT IN (SELECT BOXCODE FROM BOX_BEAN) AND TASKID IN (");
        sql.append("SELECT ID FROM TASK_BEAN WHERE TASKDATE>=?)");
        // 执行sql
        List<OpdateBean> opdateList = new ArrayList<>();
        Cursor cursor = EscortApp.getInstance().getDb().rawQuery(sql.toString(), new String[]{taskdate});
        // 获取查询结果
        while (cursor.moveToNext()) {
            OpdateBean opdate = new OpdateBean();
            opdate.setId(cursor.getString(0));
            opdate.setOpdate("");
            opdateList.add(opdate);
        }
        cursor.close();
        return opdateList;
    }

    @Override
    public void saveBox(List<BoxBean> boxBeanList) {
        EscortApp.getInstance().getDaoSession().getBoxBeanDao().insertOrReplaceInTx(boxBeanList);
    }

    @Override
    public List<OpdateBean> getEscortOpdate(String taskdate) {
        // 生成sql语句
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT ESCODE FROM TASK_ESCORT_BEAN ");
        sql.append("WHERE ESCODE NOT IN (SELECT ESCORTNO FROM ESCORT_BEAN) AND TASKID IN (");
        sql.append("SELECT ID FROM TASK_BEAN WHERE TASKDATE>=?)");
        // 执行sql
        List<OpdateBean> opdateList = new ArrayList<>();
        Cursor cursor = EscortApp.getInstance().getDaoSession().getDatabase().rawQuery(sql.toString(), new String[]{taskdate});
        // 获取查询结果
        while (cursor.moveToNext())
        {
            OpdateBean opdate = new OpdateBean();
            opdate.setId(cursor.getString(0));
            opdate.setOpdate("");
            opdateList.add(opdate);
        }
        cursor.close();
        return opdateList;
    }

    @Override
    public void saveEscort(List<EscortBean> escortBeanList) {
        EscortApp.getInstance().getDaoSession().getEscortBeanDao().insertOrReplaceInTx(escortBeanList);
    }

    @Override
    public List<OpdateBean> getBoxOpdate() {
        // 生成sql语句
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("BOXCODE");
        sql.append(",");
        sql.append("OPDATE");
        sql.append(" FROM ");
        sql.append("BOX_BEAN");
        // 执行sql
        List<OpdateBean> opdateList = new ArrayList<OpdateBean>();
        Cursor cursor = EscortApp.getInstance().getDaoSession().getDatabase().rawQuery(sql.toString(), null);
        // 获取查询结果
        while (cursor.moveToNext())
        {
            OpdateBean opdate = new OpdateBean();
            opdate.setId(fetchByName(cursor, "BOXCODE"));
            opdate.setOpdate(fetchByName(cursor, "OPDATE"));
            opdateList.add(opdate);
        }
        cursor.close();
        return opdateList;
    }

    public static String fetchByName(Cursor cursor, String fieldName)
    {
        int index = cursor.getColumnIndexOrThrow(fieldName);
        return cursor.getString(index);
    }

}
