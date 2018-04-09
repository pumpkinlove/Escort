package com.miaxis.escort.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.presenter.ILoginPresenter;
import com.miaxis.escort.presenter.LoginPresenterImpl;
import com.miaxis.escort.view.fragment.ConfigFragment;
import com.miaxis.escort.view.viewer.ILoginView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements ILoginView, ConfigFragment.OnConfigClickListener{

    private ILoginPresenter loginPresenter;

    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.fl_config)
    FrameLayout flConfig;
    @BindView(R.id.iv_config)
    ImageView ivConfig;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected int setContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenterImpl(context,this);
        loginPresenter.getPermissions(this);

    }

    @Override
    protected void initView() {
        RxView.clicks(ivConfig)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        showConfigView();
                    }
                });
    }

    @Override
    public void showLoginView() {
        ivConfig.setVisibility(View.VISIBLE);
        llLogin.setVisibility(View.VISIBLE);
        flConfig.setVisibility(View.GONE);
    }

    @Override
    public void showConfigView() {
        ivConfig.setVisibility(View.INVISIBLE);
        llLogin.setVisibility(View.GONE);
        flConfig.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_config, ConfigFragment.newInstance()).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.doDestroy();
    }

    @Override
    public void onConfigSave() {
        showLoginView();
        //TODO:登陆页面显示相关信息
    }

    @Override
    public void onConfigCancel() {
        showLoginView();
    }

    @Override
    public void getPermissionsSuccess() {
        EscortApp.getInstance().initDbHelp();
    }

    @Override
    public void getPermissionsFailed() {
        Toast.makeText(this, "拒绝权限将无法正常运行", Toast.LENGTH_LONG).show();
        finish();
    }
}
