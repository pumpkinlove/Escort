package com.miaxis.escort.view.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.miaxis.escort.R;
import com.miaxis.escort.adapter.SearchBoxAdapter;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.presenter.ISearchBoxPresenter;
import com.miaxis.escort.presenter.SearchBoxPresenterImpl;
import com.miaxis.escort.view.viewer.ISearchBoxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class SearchBoxActivity extends BaseActivity implements ISearchBoxView{

    @BindView(R.id.search_box_toolbar)
    Toolbar toolbar;
    @BindView(R.id.srl_search_box)
    SwipeRefreshLayout srlSearchBox;
    @BindView(R.id.rv_search_box)
    RecyclerView rvSearchBox;

    private ISearchBoxPresenter searchBoxPresenter;
    private SearchBoxAdapter searchBoxAdapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_search_box;
    }

    @Override
    protected void initData() {
        searchBoxPresenter = new SearchBoxPresenterImpl(this, this);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("箱包查询");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchBoxAdapter = new SearchBoxAdapter(this, new ArrayList<BoxBean>());
        rvSearchBox.setAdapter(searchBoxAdapter);
        rvSearchBox.setLayoutManager(new LinearLayoutManager(this));
        searchBoxAdapter.setOnItemClickListener(new SearchBoxAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        srlSearchBox.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlSearchBox.setRefreshing(true);
                searchBoxPresenter.downSearchBox();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchBoxPresenter.loadSearchBox();
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
    public void updateData(List<BoxBean> boxBeans) {
        searchBoxAdapter.setDataList(boxBeans);
        searchBoxAdapter.notifyDataSetChanged();
        if (srlSearchBox.isRefreshing()) {
            srlSearchBox.setRefreshing(false);
            Toasty.success(this, "刷新成功",0, true).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchBoxPresenter.doDestroy();
    }
}
