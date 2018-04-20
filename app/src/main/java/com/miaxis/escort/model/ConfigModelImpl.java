package com.miaxis.escort.model;

import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.BankBean;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.OpdateBean;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.presenter.IConfigPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 一非 on 2018/4/8.
 */

public class ConfigModelImpl implements IConfigModel{

    private IConfigPresenter configPresenter;

    public ConfigModelImpl(IConfigPresenter configPresenter) {
        this.configPresenter = configPresenter;
    }

    @Override
    public void saveConfig(Config config) {
        EscortApp.getInstance().getDaoSession().getConfigDao().deleteAll();
        EscortApp.getInstance().getDaoSession().getConfigDao().insert(config);
    }

    @Override
    public Config loadConfig() {
         return EscortApp.getInstance().getDaoSession().getConfigDao().load(1L);
    }

    @Override
    public void saveBank(BankBean bankBean) {
        EscortApp.getInstance().getDaoSession().getBankBeanDao().deleteAll();
        EscortApp.getInstance().getDaoSession().getBankBeanDao().insert(bankBean);
    }

    @Override
    public List<OpdateBean> loadWorkerOpdate() {
        List<WorkerBean> workerBeanList = EscortApp.getInstance().getDaoSession().getWorkerBeanDao().loadAll();
        List<OpdateBean> opdateBeanList = new ArrayList<>();
        for (WorkerBean workerBean : workerBeanList) {
            opdateBeanList.add(new OpdateBean(workerBean.getId(), workerBean.getOpdate()));
        }
        return opdateBeanList;
    }

    @Override
    public void saveWorker(List<WorkerBean> workerBeanList) {
        EscortApp.getInstance().getDaoSession().getWorkerBeanDao().deleteAll();
        EscortApp.getInstance().getDaoSession().getWorkerBeanDao().insertOrReplaceInTx(workerBeanList);
    }

    @Override
    public List<OpdateBean> loadBoxOpdate() {
        List<BoxBean> boxBeanList = EscortApp.getInstance().getDaoSession().getBoxBeanDao().loadAll();
        List<OpdateBean> opdateBeanList = new ArrayList<>();
        for (BoxBean boxBean : boxBeanList) {
            opdateBeanList.add(new OpdateBean(boxBean.getId(), boxBean.getOpdate()));
        }
        return opdateBeanList;
    }

    @Override
    public void saveBox(List<BoxBean> boxBeanList) {
        EscortApp.getInstance().getDaoSession().getBoxBeanDao().insertOrReplaceInTx(boxBeanList);
    }
}
