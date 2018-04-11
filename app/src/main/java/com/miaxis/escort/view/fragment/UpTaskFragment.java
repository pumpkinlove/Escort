package com.miaxis.escort.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.miaxis.escort.R;
import com.miaxis.escort.util.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UpTaskFragment extends BaseFragment {

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
    @BindView(R.id.gl_box)
    GridLayout glBox;

    private List<TextView> textViews;
    private List<TextView> boxTextViews;
    private List<String> boxList;
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
                        }
                    });
        }
        boxList = new ArrayList<>();
        boxList.add("XJBX01");
        boxList.add("XJBX02");
        boxList.add("XJBX03");
        boxList.add("XJBX04");
        boxList.add("XJBX05");
        boxList.add("XJBX06");
        boxTextViews = new ArrayList<>();
        WindowManager manager = this.getActivity().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        for (int i = 0; i < boxList.size(); i++) {
            String type = boxList.get(i);
            TextView tv = new TextView(this.getActivity());
            tv.setText(type);
            tv.setTextColor(getResources().getColor(R.color.darkgray));
            tv.setGravity(Gravity.CENTER);
            tv.setFocusable(true);
            tv.setClickable(true);
            GridLayout.Spec rowSpec = GridLayout.spec(i / 3, 1f);
            GridLayout.Spec columnSpec = GridLayout.spec(i % 3, 1f);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
            params.height = 120;
            params.width = 0;
            params.setMargins(10, 0, 10, 15);
            tv.setLayoutParams(params);
            tv.setBackground(this.getActivity().getDrawable(R.drawable.orange_check_bg));
            glBox.addView(tv);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tv = ((TextView) view);
                    if (tv.isSelected()) {
                        tv.setTextColor(getResources().getColor(R.color.darkgray));
                        tv.setSelected(false);
                    } else {
                        tv.setTextColor(getResources().getColor(R.color.black));
                        tv.setSelected(true);
                    }
                }
            });
            boxTextViews.add(tv);
        }
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
