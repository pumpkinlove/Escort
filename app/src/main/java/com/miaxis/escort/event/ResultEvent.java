package com.miaxis.escort.event;

import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.view.fragment.ExecuteTaskDialogFragment;

/**
 * Created by tang.yf on 2018/7/27.
 */

public class ResultEvent {

    public static final int VERIFY_WORKER_SUCCESS = 0;
    public static final int VERIFY_ESCORT_SUCCESS = 1;
    public static final int VERIFY_FAILED = 2;
    public static final int COMPLETE = 3;
    public static final int error = 4;

    private int result;
    private WorkerBean workerBean;
    private EscortBean escortBean;
    private int time;

    public ResultEvent(int result) {
        this.result = result;
    }

    public ResultEvent(int result, WorkerBean workerBean, int time) {
        this.result = result;
        this.workerBean = workerBean;
        this.time = time;
    }

    public ResultEvent(int result, EscortBean escortBean, int time) {
        this.result = result;
        this.escortBean = escortBean;
        this.time = time;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public WorkerBean getWorkerBean() {
        return workerBean;
    }

    public void setWorkerBean(WorkerBean workerBean) {
        this.workerBean = workerBean;
    }

    public EscortBean getEscortBean() {
        return escortBean;
    }

    public void setEscortBean(EscortBean escortBean) {
        this.escortBean = escortBean;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
