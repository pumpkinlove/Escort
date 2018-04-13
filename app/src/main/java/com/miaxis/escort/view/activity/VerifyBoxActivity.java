package com.miaxis.escort.view.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;
import com.miaxis.escort.adapter.VerifyBoxAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class VerifyBoxActivity extends BaseActivity {

    @BindView(R.id.verify_toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_verify_box)
    RecyclerView rvVerifyBox;
    @BindView(R.id.btn_verify_start)
    Button btnVerifyStart;
    @BindView(R.id.btn_verify_complete)
    Button btnVerifyComplete;

    private VerifyBoxAdapter verifyBoxAdapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_verify_box;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        toolbar.setTitle(getIntent().getStringExtra("task"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        List<String> dataList = new ArrayList<>();
        dataList.add("箱子1");
        dataList.add("箱子2");
        dataList.add("箱子3");
        dataList.add("箱子4");
        dataList.add("箱子5");
        dataList.add("箱子6");
        verifyBoxAdapter = new VerifyBoxAdapter(this, dataList);
        rvVerifyBox.setAdapter(verifyBoxAdapter);
        rvVerifyBox.setLayoutManager(new LinearLayoutManager(this));
        verifyBoxAdapter.setOnItemClickListener(new VerifyBoxAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Boolean b = verifyBoxAdapter.getCheck(position);
                ImageView imageView = view.findViewById(R.id.iv_check);
                if (b) {
                    verifyBoxAdapter.setCheck(position, false);
                    imageView.setImageResource(R.drawable.ic_uncheck_black_24dp);
                    Toasty.error(VerifyBoxActivity.this, "未完成", Toast.LENGTH_SHORT, true).show();
                } else {
                    verifyBoxAdapter.setCheck(position, true);
                    imageView.setImageResource(R.drawable.ic_check_black_24dp);
                    Toasty.success(VerifyBoxActivity.this, "已完成", Toast.LENGTH_SHORT, true).show();
                }
                if (verifyBoxAdapter.isAllCheck()) {
                    btnVerifyComplete.setEnabled(true);
                } else {
                    btnVerifyComplete.setEnabled(false);
                }
            }
        });
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
    }
}
