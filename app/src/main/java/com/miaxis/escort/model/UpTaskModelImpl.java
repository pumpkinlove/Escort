package com.miaxis.escort.model;

import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.presenter.IUpTaskPresenter;

import java.util.List;

/**
 * Created by 一非 on 2018/4/16.
 */

public class UpTaskModelImpl implements IUpTaskModel{

    private IUpTaskPresenter upTaskPresenter;

    public UpTaskModelImpl(IUpTaskPresenter upTaskPresenter) {
        this.upTaskPresenter = upTaskPresenter;
    }

    @Override
    public List<BoxBean> loadBox() {
        return EscortApp.getInstance().getDaoSession().getBoxBeanDao().loadAll();
    }
}
