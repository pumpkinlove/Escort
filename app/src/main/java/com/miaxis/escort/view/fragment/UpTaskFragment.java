package com.miaxis.escort.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.miaxis.escort.R;
import com.miaxis.escort.util.DateUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class UpTaskFragment extends BaseFragment {

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.ib_last_day)
    ImageButton ibLastDay;
    @BindView(R.id.ib_next_day)
    ImageButton ibNextDay;

    private DatePickerPopWin datePickerPopWin;
    private OnFragmentInteractionListener mListener;

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

    }

    @Override
    protected void initView() {
        tvDate.setText(DateUtil.getDayType(DateUtil.getToday()));
        datePickerPopWin = new DatePickerPopWin.Builder(UpTaskFragment.this.getActivity(), new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                tvDate.setText(DateUtil.getDayType(dateDesc));
            }
        })
                .textConfirm("确认") //text of confirm button
                .textCancel("取消") //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(25) // pick view text size
                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                .minYear(1990) //min year in loop
                .maxYear(2150) // max year in loop
                .dateChose("2018-1-2") // date chose when init popwindow
                .build();
        RxView.clicks(tvDate)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // TODO: Rename method, update argument and hook method into UI event
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
    }
}
