package com.miaxis.escort.model;

import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.OpdateBean;
import com.miaxis.escort.presenter.IUpTaskPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 一非 on 2018/4/16.
 */

public class UpTaskModelImpl implements IUpTaskModel{

    @Override
    public List<BoxBean> loadBox() {
        return EscortApp.getInstance().getDaoSession().getBoxBeanDao().loadAll();
    }

    @Override
    public List<String> loadBoxCode() {
        List<BoxBean> boxBeanList = EscortApp.getInstance().getDaoSession().getBoxBeanDao().loadAll();
        List<String> codeList = new ArrayList<>();
        for (BoxBean boxBean : boxBeanList) {
            codeList.add(boxBean.getBoxcode());
        }
        return codeList;
    }

    @Override
    public void saveBoxList(List<BoxBean> boxBeanList) {
        EscortApp.getInstance().getDaoSession().getBoxBeanDao().insertOrReplaceInTx(boxBeanList);
    }
}
