package com.miaxis.escort.view.fragment;


import android.app.ProgressDialog;
import android.content.Context;
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
import com.miaxis.escort.presenter.ConfigPresenterImpl;
import com.miaxis.escort.presenter.IConfigPresenter;
import com.miaxis.escort.view.activity.ConfigActivity;
import com.miaxis.escort.view.activity.LoginActivity;
import com.miaxis.escort.view.viewer.IConfigView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    @BindView(R.id.et_orgCode)
    EditText etOrgCode;
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
        Observable<CharSequence> observableOrgCode = RxTextView.textChanges(etOrgCode);
        Observable.combineLatest(observableIp, observablePort, observableOrgCode, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence ip, CharSequence port, CharSequence orgCode) throws Exception {
                return !ip.toString().isEmpty() && !port.toString().isEmpty() && !orgCode.toString().isEmpty();
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
                        pdSaveConfig.setContent("保存中");
                        pdSaveConfig.show();
                        configPresenter.configSave(etIp.getText().toString(),
                                etPort.getText().toString(),
                                etOrgCode.getText().toString());
                    }
                });
    }

    @Override
    public void configSaveSuccess() {
        pdSaveConfig.dismiss();
        mListener.onConfigSave();
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
        etOrgCode.setText(config.getOrgCode());
    }

    @Override
    public void setProgressDialogMessage(String message) {
        pdSaveConfig.setContent(message);
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
