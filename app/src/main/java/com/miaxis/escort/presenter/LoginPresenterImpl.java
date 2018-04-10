package com.miaxis.escort.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.miaxis.escort.model.ILoginModel;
import com.miaxis.escort.model.LoginModelImpl;
import com.miaxis.escort.view.viewer.ILoginView;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by 一非 on 2018/4/8.
 */

public class LoginPresenterImpl extends BaseActivityPresenter implements ILoginPresenter {

    private ILoginView loginView;
    private ILoginModel loginModel;

    public LoginPresenterImpl(LifecycleProvider<ActivityEvent> provider, ILoginView loginView) {
        super(provider);
        this.loginView = loginView;
        this.loginModel = new LoginModelImpl(this);
    }

    @Override
    public void getPermissions(Activity activity) {
        RxPermissions rxPermission = new RxPermissions(activity);
        rxPermission
                .requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(getProvider().<Permission>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            loginView.getPermissionsSuccess();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            loginView.getPermissionsFailed();
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            loginView.getPermissionsFailed();
                        }
                    }
                });
    }

    @Override
    public void doDestroy() {
        loginView = null;
    }
}
