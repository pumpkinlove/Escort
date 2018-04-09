package com.miaxis.escort.presenter;

import android.content.Context;
import com.miaxis.escort.model.ConfigModelImpl;
import com.miaxis.escort.model.IConfigModel;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.view.viewer.IConfigView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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

    @Override
    public void configSave(String ip, String port, String orgCode) {
        configModel.saveConfig(new Config(1L, ip, port, orgCode));
    }

    @Override
    public void configSaveSuccess() {
        configView.configSaveSuccess();
    }

    @Override
    public void configSaveFailed() {
        configView.configSaveFailed();
    }

    @Override
    public void loadConfig() {
        configModel.loadConfig();
    }

    @Override
    public void fetchConfig(Config config) {
        configView.fetchConfig(config);
    }
}
