package com.miaxis.escort.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.liulishuo.filedownloader.FileDownloader;
import com.miaxis.escort.R;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.presenter.ISystemPresenter;
import com.miaxis.escort.presenter.SystemPresenterImpl;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.activity.ConfigActivity;
import com.miaxis.escort.view.activity.LoginActivity;
import com.miaxis.escort.view.activity.QueryActivity;
import com.miaxis.escort.view.activity.WorkerManageActivity;
import com.miaxis.escort.view.viewer.ISystemView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Url;

public class SystemFragment extends BaseFragment implements ISystemView{

    @BindView(R.id.ll_query)
    LinearLayout llQuery;
    @BindView(R.id.ll_worker_manage)
    LinearLayout llWorkerManage;
    @BindView(R.id.ll_config)
    LinearLayout llConfig;
    @BindView(R.id.ll_clear_all)
    LinearLayout llClearAll;
    @BindView(R.id.ll_update)
    LinearLayout llUpdate;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    @BindView(R.id.btn_logout)
    Button btnLogout;

    private OnFragmentInteractionListener mListener;
    private ISystemPresenter systemPresenter;
    private boolean updateFlag = false;

    public SystemFragment() {
        // Required empty public constructor
    }

    public static SystemFragment newInstance() {
        SystemFragment fragment = new SystemFragment();
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_system;
    }

    @Override
    protected void initData() {
        systemPresenter = new SystemPresenterImpl(this, this);
    }

    @Override
    protected void initView() {
        RxView.clicks(llQuery)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Intent intent = new Intent(SystemFragment.this.getActivity(), QueryActivity.class);
                        startActivity(intent);
                    }
                });
        RxView.clicks(llWorkerManage)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Intent intent = new Intent(SystemFragment.this.getActivity(), WorkerManageActivity.class);
                        startActivity(intent);
                    }
                });
        RxView.clicks(llConfig)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Intent intent = new Intent(SystemFragment.this.getActivity(), ConfigActivity.class);
                        startActivity(intent);
                    }
                });
        RxView.clicks(llClearAll)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        new MaterialDialog.Builder(SystemFragment.this.getContext())
                                .title("恢复出厂设置")
                                .content("将清空数据库并退出，确认执行吗？")
                                .positiveText("确认")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                        new MaterialDialog.Builder(SystemFragment.this.getContext())
                                                .title("请稍后...")
                                                .content("正在恢复出厂设置...")
                                                .progress(true, 100)
                                                .cancelable(false)
                                                .show();
                                        systemPresenter.clearAll();
                                    }
                                })
                                .negativeText("取消")
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                });
        RxView.clicks(llUpdate)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        systemPresenter.updateApp();
                    }
                });
        RxView.clicks(btnLogout)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        new MaterialDialog.Builder(SystemFragment.this.getContext())
                                .title("退出登陆")
                                .content("    确认退出登陆吗？")
                                .positiveText("确认")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(SystemFragment.this.getActivity(), LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                })
                                .negativeText("取消")
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                });
        RxView.clicks(llAbout)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        new MaterialDialog.Builder(SystemFragment.this.getContext())
                                .title("押运终端 V" + getVersion())
                                .content(R.string.help_dialog_text)
                                .positiveText("确认")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                });
    }

    private String getVersion() {
        // 得到系统的包管理器。已经得到了apk的面向对象的包装
        PackageManager pm = this.getActivity().getPackageManager();
        try {
            // 参数一：当前应用程序的包名 参数二：可选的附加消息，这里我们用不到 ，可以定义为0
            PackageInfo info = pm.getPackageInfo(getActivity().getPackageName(), 0);
            // 返回当前应用程序的版本号
            return info.versionName;
        } catch (Exception e) {// 包名未找到的异常，理论上， 该异常不可能会发生
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void clearSuccess() {
        Toasty.success(EscortApp.getInstance().getApplicationContext(), "恢复出厂设置成功", 0, true).show();
        Intent intent = new Intent(SystemFragment.this.getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void clearFailed() {
        Toasty.error(this.getContext(), "恢复出厂设置失败", 0, true).show();
    }

    @Override
    public void downAppMessageSuccess(String message) {
        StringBuilder content = new StringBuilder();
        final String[] data = message.split("&");
        if (data[0].equals(getVersion())) {
            content.append("当前已经是最新版本，无需更新！");
            updateFlag = false;
        } else {
            content.append("最新版本为：V" + data[0] + "\n");
            content.append("当前版本为：V" + getVersion() + "\n");
            content.append("安装包大小：" + StaticVariable.convertFileSize(Long.parseLong(data[1])) + "\n");
            content.append("更新时间：" + data[2]);
            Log.e("asd", data[3]);
            updateFlag = true;
        }
        new MaterialDialog.Builder(this.getContext())
                .title("更新")
                .content(content.toString())
                .negativeText("取消")
                .positiveText("确认")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (updateFlag) {
                            materialDialog = new MaterialDialog.Builder(SystemFragment.this.getContext())
                                    .title("下载进度")
                                    .progress(false, 100)
                                    .positiveText("取消")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            FileDownloader.getImpl().pauseAll();
                                        }
                                    })
                                    .cancelable(false)
                                    .show();
                            try {
                                systemPresenter.download(new URL(data[3]), Environment.getExternalStorageDirectory().getPath()+"/xunjian V" + data[0] + ".apk");
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .show();
    }
    MaterialDialog materialDialog;

    @Override
    public void updateProgress(int progress) {
        if (materialDialog.isShowing()) {
            materialDialog.setProgress(progress);
        }
    }

    @Override
    public void downAppMessageFailed(String message) {
        Toasty.error(this.getContext(), message, 1, true).show();
    }

    @Override
    public void downloadSuccess(String path) {
        if (materialDialog.isShowing()) {
            materialDialog.setProgress(100);
            materialDialog.dismiss();
        }
        Toasty.success(this.getContext(), "下载成功", 1, true).show();
        //File file = new File(path);
        //Uri apkUri = FileProvider.getUriForFile(SystemFragment.this.getContext(), "com.miaxis.escort.fileprovider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(path), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivity(intent);
    }

    @Override
    public void downloadFailed(String message) {
        Toasty.error(this.getContext(), message, 1, true).show();
    }

    @Override
    public void downloadPause() {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        Toasty.error(this.getContext(), "取消下载", 1, true).show();
    }

    @Override
    public void onDestroy() {
        systemPresenter.doDestroy();
        super.onDestroy();
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onSystemFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onSystemFragmentInteraction();
    }
}
