package com.miaxis.escort.presenter;

import android.content.Context;

import com.miaxis.escort.model.ConfigModelImpl;
import com.miaxis.escort.model.IConfigModel;
import com.miaxis.escort.view.viewer.IConfigView;

/**
 * Created by 一非 on 2018/4/8.
 */

public class ConfigPresenterImpl extends BasePresenter implements IConfigPresenter{

    private IConfigView configView;
    private IConfigModel configModel;

    public ConfigPresenterImpl(Context context, IConfigView configView) {
        super(context);
        this.configView = configView;
        this.configModel = new ConfigModelImpl(this);
    }

}
