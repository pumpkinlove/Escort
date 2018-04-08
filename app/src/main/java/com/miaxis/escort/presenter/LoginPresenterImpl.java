package com.miaxis.escort.presenter;

import android.content.Context;

import com.miaxis.escort.model.ILoginModel;
import com.miaxis.escort.model.LoginModelImpl;
import com.miaxis.escort.view.viewer.ILoginView;

/**
 * Created by 一非 on 2018/4/8.
 */

public class LoginPresenterImpl extends BasePresenter implements ILoginPresenter {

    private ILoginView loginView;
    private ILoginModel loginModel;

    public LoginPresenterImpl(Context context, ILoginView loginView) {
        super(context);
        this.loginView = loginView;
        this.loginModel = new LoginModelImpl(this);
    }

}
