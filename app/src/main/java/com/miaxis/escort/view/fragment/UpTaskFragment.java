package com.miaxis.escort.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.miaxis.escort.R;
import com.miaxis.escort.adapter.BoxAdapter;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.TaskBoxBean;
import com.miaxis.escort.model.entity.TaskUpBean;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.presenter.IUpTaskPresenter;
import com.miaxis.escort.presenter.UpTaskPresenterImpl;
import com.miaxis.escort.util.DateUtil;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.IUpTaskView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UpTaskFragment extends BaseFragment implements IUpTaskView{

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.ib_last_day)
    ImageButton ibLastDay;
    @BindView(R.id.ib_next_day)
    ImageButton ibNextDay;
    @BindView(R.id.tv_receive_convention)
    TextView tvReceiveConvention;
    @BindView(R.id.tv_send_convention)
    TextView tvSendConvention;
    @BindView(R.id.tv_receive_temporary)
    TextView tvReceiveTemporary;
    @BindView(R.id.tv_send_temporary)
    TextView tvSendTemporary;
    @BindView(R.id.et_temporary_bank)
    EditText etTemporaryBank;
    @BindView(R.id.srl_up_task_box)
    SwipeRefreshLayout srlUpTaskBox;
    @BindView(R.id.rv_box)
    RecyclerView rvBox;
    @BindView(R.id.btn_up_task)
    Button btnUpTask;

    private List<TextView> textViews;
    private BoxAdapter boxAdapter;
    private DatePickerPopWin datePickerPopWin;
    private OnFragmentInteractionListener mListener;
    private IUpTaskPresenter upTaskPresenter;
    private String selectedType = "";
    private MaterialDialog materialDialog;

    public UpTaskFragment() {
        // Required empty public constructor
    }

    public static UpTaskFragment newInstance() {
        UpTaskFragment fragment = new UpTaskFragment();
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_up_task;
    }

    @Override
    protected void initData() {
        upTaskPresenter = new UpTaskPresenterImpl(this, this);
    }

    @Override
    protected void initView() {
        tvDate.setText(DateUtil.getDayType(DateUtil.getToday()));
        RxView.clicks(tvDate)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        datePickerPopWin = new DatePickerPopWin.Builder(UpTaskFragment.this.getActivity(), new DatePickerPopWin.OnDatePickedListener() {
                            @Override
                            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                                if (!DateUtil.isYesterday(dateDesc)) {
                                    tvDate.setText(DateUtil.getDayType(dateDesc));
                                }
                            }
                        })
                                .textConfirm("确认") //text of confirm button
                                .textCancel("取消") //text of cancel button
                                .btnTextSize(16) // button text size
                                .viewTextSize(25) // pick view text size
                                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                                .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                                .minYear(DateUtil.getYear()) //min year in loop
                                .maxYear(DateUtil.getYear() + 10) // max year in loop
                                .dateChose(tvDate.getText().toString().substring(0,10)) // date chose when init popwindow
                                .build();
                        datePickerPopWin.showPopWin(UpTaskFragment.this.getActivity());
                    }
                });
        RxTextView.textChanges(tvDate)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.<CharSequence>bindToLifecycle())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        if (TextUtils.equals(DateUtil.getDayType(DateUtil.getToday()), new StringBuilder(charSequence).toString())) {
                            ibLastDay.setEnabled(false);
                        } else {
                            ibLastDay.setEnabled(true);
                        }
                    }
                });
        RxView.clicks(ibLastDay)
                .throttleFirst(250, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        tvDate.setText(DateUtil.getDayType(DateUtil.getLastDay(tvDate.getText().toString().substring(0,10))));
                    }
                });
        RxView.clicks(ibNextDay)
                .throttleFirst(250, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        tvDate.setText(DateUtil.getDayType(DateUtil.getNextDay(tvDate.getText().toString().substring(0,10))));
                    }
                });
        textViews = new ArrayList<>();
        textViews.add(tvReceiveConvention);
        textViews.add(tvSendConvention);
        textViews.add(tvReceiveTemporary);
        textViews.add(tvSendTemporary);
        for(final TextView textView : textViews) {
            RxView.clicks(textView)
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .compose(this.bindToLifecycle())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            for (TextView tv : textViews) {
                                tv.setSelected(false);
                            }
                            textView.setSelected(true);
                            selectedType = textView.getText().toString();
                            if (textView.equals(tvReceiveTemporary) || textView.equals(tvSendTemporary)) {
                                etTemporaryBank.setVisibility(View.VISIBLE);
                            } else {
                                etTemporaryBank.setVisibility(View.GONE);
                            }
                        }
                    });
        }
        boxAdapter = new BoxAdapter(this.getActivity(), new ArrayList<BoxBean>());
        rvBox.setAdapter(boxAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 3);
        rvBox.setLayoutManager(gridLayoutManager);
        boxAdapter.setOnItemClickListener(new BoxAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView textView = (TextView) view;
                if(textView.isSelected()) {
                    textView.setSelected(false);
                    BoxBean box = boxAdapter.getData(position);
                    boxAdapter.removeSelectedBox(box);
                } else {
                    textView.setSelected(true);
                    BoxBean box = boxAdapter.getData(position);
                    boxAdapter.addSelectedBox(box);
                }
            }
        });
        RxView.clicks(btnUpTask)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (!checkNotNull()) {
                            return;
                        }
                        String temporaryId = "";
                        if ("临时网点接箱".equals(selectedType) || "临时网点送箱".equals(selectedType)) {
                            temporaryId = "(" + etTemporaryBank.getText().toString() + ")";
                        }
                        StringBuilder builder = new StringBuilder();
                        builder.append("任务清单：" + "\n")
                                .append("    " + tvDate.getText().toString() + "\n")
                                .append("    " + selectedType + temporaryId + "\n")
                                .append(boxAdapter.makeSelectedText());
                        new MaterialDialog.Builder(UpTaskFragment.this.getActivity())
                                .title("确认任务清单")
                                .content(builder.toString())
                                .negativeText("取消")
                                .positiveText("确认")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        materialDialog = new MaterialDialog.Builder(UpTaskFragment.this.getActivity())
                                                .title("请稍后...")
                                                .content("正在上传任务信息...")
                                                .progress(true, 100)
                                                .cancelable(false)
                                                .show();
                                        Config config = (Config) EscortApp.getInstance().get(StaticVariable.CONFIG);
                                        WorkerBean workerBean = (WorkerBean) EscortApp.getInstance().get(StaticVariable.WORKER);
                                        TaskUpBean taskUpBean = new TaskUpBean();
                                        taskUpBean.setTaskcode(config.getOrgCode() + DateUtil.getCurDateTime());
                                        taskUpBean.setTaskseq("0");
                                        taskUpBean.setTasktype(StaticVariable.upTaskTypeTurnToString(selectedType));
                                        taskUpBean.setDeptno(config.getOrgCode());
                                        taskUpBean.setDeptno2(etTemporaryBank.getText().toString());
                                        taskUpBean.setOpuser(workerBean.getOpuser());
                                        taskUpBean.setOpusername(workerBean.getOpusername());
                                        taskUpBean.setTaskdate(tvDate.getText().toString().substring(0,10));
                                        upTaskPresenter.upTask(taskUpBean, boxAdapter.getSelectedTaskBoxList(taskUpBean.getTaskcode()));
                                    }
                                })
                                .show();
                    }
                });
        srlUpTaskBox.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                materialDialog = new MaterialDialog.Builder(UpTaskFragment.this.getActivity())
                        .title("请稍后...")
                        .content("正在更新箱包信息...")
                        .progress(true, 100)
                        .cancelable(false)
                        .show();
                upTaskPresenter.downBox();
            }
        });
    }

    /**
     * 上传任务类型箱包编号判空
     * @return
     */
    public boolean checkNotNull() {
        if ("".equals(selectedType)) {
            Toasty.error(this.getActivity(), "未选择类型", 0, true).show();
            return false;
        }
        if (boxAdapter.getSelectedSize() == 0) {
            Toasty.error(this.getActivity(), "未选择箱包", 0, true).show();
            return false;
        }
        return true;
    }

    @Override
    public void upTaskSuccess() {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        Toasty.success(this.getActivity(), "上传成功", 0, true).show();
        clearSelected();
        mListener.refreshTask();
    }

    @Override
    public void upTaskFailed(String message) {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        Toasty.error(this.getActivity(), message, 0, true).show();
    }

    /**
     * 清空已选择的箱包
     */
    private void clearSelected() {
        etTemporaryBank.setText("");
        boxAdapter.clearSelected();
        boxAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        upTaskPresenter.loadBox();
    }

    @Override
    public void updateBox(List<BoxBean> boxBeanList) {
        if (boxBeanList != null) {
            boxAdapter.setDataList(boxBeanList);
            boxAdapter.clearSelected();
            boxAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void downBoxSuccess() {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        if (srlUpTaskBox.isRefreshing()) {
            srlUpTaskBox.setRefreshing(false);
        }
        upTaskPresenter.loadBox();
    }

    @Override
    public void downBoxFailed(String message) {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        if (srlUpTaskBox.isRefreshing()) {
            srlUpTaskBox.setRefreshing(false);
        }
        Toasty.error(EscortApp.getInstance().getApplicationContext(), message, 0, true).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        upTaskPresenter.doDestroy();
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onUpTaskFragmentInteraction();
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
        void onUpTaskFragmentInteraction();
        void refreshTask();
    }
}
