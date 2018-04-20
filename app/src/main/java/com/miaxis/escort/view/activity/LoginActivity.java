package com.miaxis.escort.view.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.presenter.ILoginPresenter;
import com.miaxis.escort.presenter.LoginPresenterImpl;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.fragment.ConfigFragment;
import com.miaxis.escort.view.viewer.ILoginView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
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

    private MaterialDialog materialDialog;

    @Override
    protected int setContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenterImpl(this,this);
        loginPresenter.getPermissions(this);

    }

    @Override
    protected void initView() {
        tvVersion.setText(tvVersion.getText() + getVersion());
        materialDialog = new MaterialDialog.Builder(this)
                .content("")
                .progress(true, 100)
                .cancelable(false)
                .build();
        RxView.clicks(ivConfig)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        showConfigView();
                    }
                });
        RxView.clicks(btnLogin)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        //TODO:登陆指纹验证
                        loginPresenter.initAppData();
                    }
                });
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFailed() {
        Toasty.error(this, "登录失败",0, true).show();
    }

    private String getVersion() {
        // 得到系统的包管理器。已经得到了apk的面向对象的包装
        PackageManager pm = this.getPackageManager();
        try {
            // 参数一：当前应用程序的包名 参数二：可选的附加消息，这里我们用不到 ，可以定义为0
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            // 返回当前应用程序的版本号
            return info.versionName;
        } catch (Exception e) {// 包名未找到的异常，理论上， 该异常不可能会发生
            e.printStackTrace();
            return "";
        }
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
    public void setDialogMessage(String message) {
        materialDialog.setContent(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.doDestroy();
    }

    @Override
    public void onConfigSave() {
        showLoginView();
        Toasty.success(this, "信息下载成功", 0, true).show();
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
        Toasty.error(this, "拒绝权限将无法正常运行", Toast.LENGTH_LONG, true).show();
        finish();
    }
}
