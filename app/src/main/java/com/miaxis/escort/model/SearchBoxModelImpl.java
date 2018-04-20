package com.miaxis.escort.model;

import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.OpdateBean;
import com.miaxis.escort.presenter.ISearchBoxPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 一非 on 2018/4/17.
 */

public class SearchBoxModelImpl implements ISearchBoxModel{

    @Override
    public List<BoxBean> loadSearchBox() {
        return EscortApp.getInstance().getDaoSession().getBoxBeanDao().loadAll();
    }

    @Override
    public void saveSearchBox(List<BoxBean> boxBeans) {
        EscortApp.getInstance().getDaoSession().getBoxBeanDao().insertOrReplaceInTx(boxBeans);
    }

    @Override
    public List<OpdateBean> getBoxOpdate() {
        List<BoxBean> boxBeanList = EscortApp.getInstance().getDaoSession().getBoxBeanDao().loadAll();
        List<OpdateBean> opdateBeanList = new ArrayList<>();
        for (BoxBean boxBean : boxBeanList) {
            opdateBeanList.add(new OpdateBean(boxBean.getId(), boxBean.getOpdate()));
        }
        return opdateBeanList;
    }
}
