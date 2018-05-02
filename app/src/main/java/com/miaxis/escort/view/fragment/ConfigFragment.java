package com.miaxis.escort.view.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.miaxis.escort.R;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.presenter.ConfigPresenterImpl;
import com.miaxis.escort.presenter.IConfigPresenter;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.activity.ConfigActivity;
import com.miaxis.escort.view.activity.LoginActivity;
import com.miaxis.escort.view.activity.WorkerDetailActivity;
import com.miaxis.escort.view.activity.WorkerManageActivity;
import com.miaxis.escort.view.viewer.IConfigView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigFragment extends BaseFragment implements IConfigView{

    private IConfigPresenter configPresenter;
    private OnConfigClickListener mListener;

    private MaterialDialog pdSaveConfig;

    @BindView(R.id.et_ip)
    EditText etIp;
    @BindView(R.id.et_port)
    EditText etPort;
    /*@BindView(R.id.et_orgCode)
    EditText etOrgCode;*/
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    public ConfigFragment() {
        // Required empty public constructor
    }

    public static ConfigFragment newInstance() {
        return new ConfigFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ConfigFragment.OnConfigClickListener) {
            mListener = (ConfigFragment.OnConfigClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnConfigClickListener");
        }
    }

    @Override
    protected int setContentView() {
        if (getActivity() instanceof ConfigActivity) {
            return R.layout.fragment_config_black;
        }
        return R.layout.fragment_config;
    }

    @Override
    protected void initData() {
        configPresenter = new ConfigPresenterImpl(this, this);
        configPresenter.loadConfig();
    }

    @Override
    protected void initView() {
        pdSaveConfig = new MaterialDialog.Builder(this.getActivity())
                .title("请稍后...")
                .content("")
                .progress(true, 100)
                .cancelable(false)
                .build();
        RxView.clicks(btnCancel)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mListener.onConfigCancel();
                    }
                });
        Observable<CharSequence> observableIp = RxTextView.textChanges(etIp);
        Observable<CharSequence> observablePort = RxTextView.textChanges(etPort);
        Observable.combineLatest(observableIp, observablePort, new BiFunction<CharSequence, CharSequence, Boolean>(){
            @Override
            public Boolean apply(CharSequence ip, CharSequence port) throws Exception {
                return !ip.toString().isEmpty() && !port.toString().isEmpty();
            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.<Boolean>bindToLifecycle())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean b) throws Exception {
                        if (b != null) {
                            btnConfirm.setEnabled(b.booleanValue());
                        }
                    }
                });
        RxView.clicks(btnConfirm)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        pdSaveConfig.setContent("正在清空数据...");
                        pdSaveConfig.show();
                        configPresenter.configSave(etIp.getText().toString(),
                                etPort.getText().toString());
                    }
                });
    }

    @Override
    public void configSaveSuccess() {
        pdSaveConfig.dismiss();
        mListener.onConfigSave();
        configPresenter.initWorker();
    }

    @Override
    public void configSaveFailed() {
        pdSaveConfig.setCancelable(true);
    }

    @Override
    public void fetchConfig(Config config) {
        if (config == null) {
            return;
        }
        etIp.setText(config.getIp());
        etPort.setText(config.getPort());
    }

    @Override
    public void setProgressDialogMessage(String message) {
        pdSaveConfig.setContent(message);
    }

    @Override
    public void initWorker() {
        WorkerBean superWorker = new WorkerBean();
        superWorker.setWorkno("admin");
        superWorker.setName("超级操作员");
        EscortApp.getInstance().put(StaticVariable.WORKER, superWorker);
        Intent intent = new Intent(ConfigFragment.this.getActivity(), WorkerDetailActivity.class);
        intent.putExtra(StaticVariable.FLAG, 1);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        configPresenter.doDestroy();
    }

    public interface OnConfigClickListener {
        void onConfigSave();
        void onConfigCancel();
    }

}
