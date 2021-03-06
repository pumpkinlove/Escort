package com.miaxis.escort.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.presenter.ILoginPresenter;
import com.miaxis.escort.presenter.LoginPresenterImpl;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.fragment.ConfigFragment;
import com.miaxis.escort.view.viewer.ILoginView;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity implements ILoginView, ConfigFragment.OnConfigClickListener, TextToSpeech.OnInitListener {

    private ILoginPresenter loginPresenter;

    @BindView(R.id.fl_config)
    FrameLayout flConfig;
    @BindView(R.id.iv_config)
    ImageView ivConfig;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    private MaterialDialog materialDialog;

    private WeakReference<TextToSpeech> ttsRef;

    @Override
    protected int setContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        //TODO:网点员工默认图片更换
        //TODO:主界面按返回键退出
        loginPresenter = new LoginPresenterImpl(this,this);
        loginPresenter.getPermissions(this);
        ttsRef = new WeakReference<TextToSpeech>(new TextToSpeech(this, this));
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        tvVersion.append(getVersion());
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
                        if (loginPresenter.loadWorkerSize() == 0) {
                            Toasty.error(EscortApp.getInstance().getApplicationContext(), "未找到员工信息，请尝试重新设置IP和端口",1, true).show();
                        } else {
                            playVoiceMessage("请按手指");
                            loginPresenter.login();
//                            loginPresenter.initAppData();
                        }
                    }
                });
        loginPresenter.resumeTaskExe();
    }

    @Override
    public void loginSuccess(WorkerBean workerBean) {
        playVoiceMessage("指纹验证通过");
        Toasty.success(EscortApp.getInstance().getApplicationContext(), "欢迎您，" + workerBean.getName(), 0, true).show();
        EscortApp.getInstance().clearMap();
        EscortApp.getInstance().put(StaticVariable.WORKER, workerBean);
        EscortApp.getInstance().put(StaticVariable.CONFIG, EscortApp.getInstance().getDaoSession().getConfigDao().load(1L));
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFailed(String message) {
        //Toasty.error(this, message,0, true).show();
        playVoiceMessage("登录失败，请重试");
        //Toasty.error(this, "登录失败，请重试",0, true).show();
        Toasty.error(this, message,0, true).show();
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
        btnLogin.setVisibility(View.VISIBLE);
        flConfig.setVisibility(View.GONE);
    }

    @Override
    public void showConfigView() {
        ivConfig.setVisibility(View.INVISIBLE);
        btnLogin.setVisibility(View.GONE);
        flConfig.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_config, ConfigFragment.newInstance()).commit();
    }

    @Override
    public void loadConfigFailed() {
        showConfigView();
    }

    @Override
    public void setDialogMessage(String message) {
        materialDialog.setContent(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.doDestroy();
        ttsRef.get().shutdown();
    }

    @Override
    public void onConfigSave() {
        showLoginView();
        Toasty.success(this, "信息下载成功", 0, true).show();
    }

    @Override
    public void onConfigCancel() {
        showLoginView();
    }

    @Override
    public void getPermissionsSuccess() {
        EscortApp.getInstance().initDbHelp();
        loginPresenter.loadConfig();
    }

    @Override
    public void getPermissionsFailed() {
        Toasty.error(this, "拒绝权限将无法正常运行", 0, true).show();
        finish();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
        ttsRef.get().setLanguage(Locale.CHINESE);
    }
}

    public void playVoiceMessage(String message) {
        ttsRef.get().speak(message, TextToSpeech.QUEUE_FLUSH, null, "login");
    }

}
