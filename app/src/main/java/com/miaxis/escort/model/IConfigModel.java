package com.miaxis.escort.model;

import com.miaxis.escort.model.entity.BankBean;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.OpdateBean;
import com.miaxis.escort.model.entity.WorkerBean;

import java.util.List;

/**
 * Created by 一非 on 2018/4/8.
 */

public interface IConfigModel {
    void saveConfig(Config config);
    Config loadConfig();
    void saveBank(BankBean bankBean);
    List<OpdateBean> loadWorkerOpdate();
    void saveWorker(List<WorkerBean> workerBeanList);
    List<OpdateBean> loadBoxOpdate();
    void saveBox(List<BoxBean> boxBeanList);
}
