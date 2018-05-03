package com.miaxis.escort.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;
import com.miaxis.escort.presenter.FingerPresenterImpl;
import com.miaxis.escort.presenter.IFingerPresenter;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.IFingerView;

import java.util.Objects;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class FingerActivity extends BaseActivity implements IFingerView{

    @BindView(R.id.finger_toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_finger)
    ImageView ivFinger;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    private IFingerPresenter fingerPresenter;

    @Override
    protected int setContentView() {
        return R.layout.activity_finger;
    }

    @Override
    protected void initData() {
        fingerPresenter = new FingerPresenterImpl(this, this);

    }

    @Override
    protected void initView() {
        toolbar.setTitle("押运员查询");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RxView.clicks(btnCancel)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        onBackPressed();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fingerPresenter.regFinger();
    }

    @Override
    public void updateTip(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvTip.setText(message);
            }
        });
    }

    @Override
    public void updateImage(final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ivFinger.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void register(final String finger) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putExtra("finger", finger);
                setResult(1, intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(0);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fingerPresenter.doDestroy();
    }
}
