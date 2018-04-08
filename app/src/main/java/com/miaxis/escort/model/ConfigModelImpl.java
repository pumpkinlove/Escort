package com.miaxis.escort.model;

import com.miaxis.escort.presenter.IConfigPresenter;

/**
 * Created by 一非 on 2018/4/8.
 */

public class ConfigModelImpl implements IConfigModel{

    private IConfigPresenter configPresenter;

    public ConfigModelImpl(IConfigPresenter configPresenter) {
        this.configPresenter = configPresenter;
    }

}
