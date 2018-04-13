package com.miaxis.escort.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.miaxis.escort.R;
import com.miaxis.escort.adapter.SearchEscortAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchEscortActivity extends BaseActivity {

    @BindView(R.id.search_escort_toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_search_escort)
    RecyclerView rvSearchEscort;

    private SearchEscortAdapter searchEscortAdapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_search_escort;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        toolbar.setTitle("押运员查询");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        List<String> dataList = new ArrayList<>();
        dataList.add("押运员1");
        dataList.add("押运员2");
        dataList.add("押运员3");
        dataList.add("押运员4");
        dataList.add("押运员5");
        dataList.add("押运员6");
        searchEscortAdapter = new SearchEscortAdapter(this, dataList);
        rvSearchEscort.setAdapter(searchEscortAdapter);
        rvSearchEscort.setLayoutManager(new LinearLayoutManager(this));
        searchEscortAdapter.setOnItemClickListener(new SearchEscortAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

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
