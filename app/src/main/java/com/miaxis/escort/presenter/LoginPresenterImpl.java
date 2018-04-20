package com.miaxis.escort.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.ILoginModel;
import com.miaxis.escort.model.LoginModelImpl;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.model.retrofit.EscortNet;
import com.miaxis.escort.model.retrofit.ResponseEntity;
import com.miaxis.escort.model.retrofit.TaskNet;
import com.miaxis.escort.util.DateUtil;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.ILoginView;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 一非 on 2018/4/8.
 */

public class LoginPresenterImpl extends BaseActivityPresenter implements ILoginPresenter {

    private ILoginView loginView;
    private ILoginModel loginModel;

    public LoginPresenterImpl(LifecycleProvider<ActivityEvent> provider, ILoginView loginView) {
        super(provider);
        this.loginView = loginView;
        this.loginModel = new LoginModelImpl();
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
    public void initAppData() {
        Observable.create(new ObservableOnSubscribe<Config>() {
            @Override
            public void subscribe(ObservableEmitter<Config> e) throws Exception {
                e.onNext(loginModel.loadConfig());
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<Config>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        List<WorkerBean> workerBeanList = EscortApp.getInstance().getDaoSession().getWorkerBeanDao().loadAll();
                        //TODO:默认登陆员工
                        EscortApp.getInstance().put(StaticVariable.WORKER, workerBeanList.get(0));
                        EscortApp.getInstance().put(StaticVariable.CONFIG, config);
                        loginView.loginSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginView.loginFailed();
                    }
                });
    }

    @Override
    public void doDestroy() {
        loginView = null;
        loginModel = null;
    }
}
