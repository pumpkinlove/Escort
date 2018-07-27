package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tang.yf on 2018/7/27.
 */

@Entity
public class TaskExeLog {
    @Id
    private String taskCode;
    private String workerCode;
    private String workerName;
    private String escortCode;
    private String escortName;
    @Generated(hash = 1483262845)
    public TaskExeLog(String taskCode, String workerCode, String workerName,
            String escortCode, String escortName) {
        this.taskCode = taskCode;
        this.workerCode = workerCode;
        this.workerName = workerName;
        this.escortCode = escortCode;
        this.escortName = escortName;
    }
    @Generated(hash = 188183199)
    public TaskExeLog() {
    }
    public String getTaskCode() {
        return this.taskCode;
    }
    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }
    public String getWorkerCode() {
        return this.workerCode;
    }
    public void setWorkerCode(String workerCode) {
        this.workerCode = workerCode;
    }
    public String getWorkerName() {
        return this.workerName;
    }
    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }
    public String getEscortCode() {
        return this.escortCode;
    }
    public void setEscortCode(String escortCode) {
        this.escortCode = escortCode;
    }
    public String getEscortName() {
        return this.escortName;
    }
    public void setEscortName(String escortName) {
        this.escortName = escortName;
    }
    
}
