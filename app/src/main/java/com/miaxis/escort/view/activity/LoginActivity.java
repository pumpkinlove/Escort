package com.miaxis.escort.view.activity;

import android.widget.Button;

import com.miaxis.escort.R;
import com.miaxis.escort.present.ILoginPresenter;
import com.miaxis.escort.present.LoginPresenterImpl;
import com.miaxis.escort.view.ILoginView;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements ILoginView{

    private ILoginPresenter loginPresenter;

    @BindView(R.id.btn_login) Button btnLogin;

    @Override
    protected int setContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenterImpl(context,this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.doDestroy();
    }

}
