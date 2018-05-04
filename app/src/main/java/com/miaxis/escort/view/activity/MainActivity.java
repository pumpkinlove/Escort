package com.miaxis.escort.view.activity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.miaxis.escort.R;
import com.miaxis.escort.adapter.MainFragmentAdapter;
import com.miaxis.escort.view.fragment.MyTaskFragment;
import com.miaxis.escort.view.fragment.SystemFragment;
import com.miaxis.escort.view.fragment.UpTaskFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindColor;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements UpTaskFragment.OnFragmentInteractionListener,
        MyTaskFragment.OnFragmentInteractionListener,
        SystemFragment.OnFragmentInteractionListener, BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp_main)
    ViewPager vpMain;

    @BindColor(R.color.blue_band_dark)
    int cBandBlueDark;
    @BindView(R.id.bnv_main)
    BottomNavigationView bnvMain;

    @BindArray(R.array.title)
    String[] titles;

    private MainFragmentAdapter adapter;

    private UpTaskFragment upTaskFragment;
    private MyTaskFragment myTaskFragment;
    private SystemFragment systemFragment;
    private MenuItem menuItem;


    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        List<Fragment> fragmentList = new ArrayList<>();
        upTaskFragment = UpTaskFragment.newInstance();
        fragmentList.add(upTaskFragment);
        myTaskFragment = MyTaskFragment.newInstance();
        fragmentList.add(myTaskFragment);
        systemFragment = SystemFragment.newInstance();
        fragmentList.add(systemFragment);
        adapter = new MainFragmentAdapter(getSupportFragmentManager(), fragmentList);
    }

    @Override
    protected void initView() {
        vpMain.setAdapter(adapter);
        vpMain.addOnPageChangeListener(this);
        vpMain.setOffscreenPageLimit(20);
        vpMain.setCurrentItem(1);
        bnvMain.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void onUpTaskFragmentInteraction() {

    }

    @Override
    public void refreshTask() {
        myTaskFragment.refreshTaskAfterUpTask();
    }

    @Override
    public void onMyTaskFragmentInteraction() {

    }

    @Override
    public void onSystemFragmentInteraction() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_upload_task:
                vpMain.setCurrentItem(0);
                break;
            case R.id.action_my_task:
                vpMain.setCurrentItem(1);
                break;
            case R.id.action_setting:
                vpMain.setCurrentItem(2);
                break;
        }
        return false;

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        toolbar.setTitle(titles[position]);
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            bnvMain.getMenu().getItem(0).setChecked(false);
        }
        menuItem = bnvMain.getMenu().getItem(position);
        menuItem.setChecked(true);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
    }
}
