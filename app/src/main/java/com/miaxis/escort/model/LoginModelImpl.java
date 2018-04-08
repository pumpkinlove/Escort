package com.miaxis.escort.model;

import com.miaxis.escort.presenter.ILoginPresenter;

/**
 * Created by 一非 on 2018/4/8.
 */

public class LoginModelImpl implements ILoginModel{

    private ILoginPresenter loginPresent;

    public LoginModelImpl(ILoginPresenter loginPresent) {
        this.loginPresent = loginPresent;
    }

}
