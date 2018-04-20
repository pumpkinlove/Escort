package com.miaxis.escort.view.viewer;

/**
 * Created by 一非 on 2018/4/19.
 */

public interface IWorkerDetailView {
    void addWorkerSuccess();
    void addWorkerFailed(String message);
    void delWorkerSuccess();
    void delWorkerFailed(String message);
}
