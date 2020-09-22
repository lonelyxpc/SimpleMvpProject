package com.lonelyxpc.mvp.module.MainActivity.view;

import android.os.Bundle;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.lonelyxpc.mvp.R;
import com.lonelyxpc.mvp.base.BaseActivity;
import com.lonelyxpc.mvp.module.MainActivity.adapter.HomePageFragmentAdapter;
import com.lonelyxpc.mvp.module.MainActivity.fragment.AboutFragment;
import com.lonelyxpc.mvp.module.MainActivity.fragment.HomeFragment;
import com.lonelyxpc.mvp.module.MainActivity.presenter.MainActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<IMainActivityView, MainActivityPresenter> implements IMainActivityView, ViewPager.OnPageChangeListener {


    @BindView(R.id.vp_home)
    ViewPager vpHome;
    @BindView(R.id.tab_bottom)
    TabLayout tabBottom;
    List<Fragment> list;
    List<String> titles;
    @Override
    public MainActivityPresenter initPresenter() {
        return new MainActivityPresenter();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        initViewPage();
        immersiveTop(R.id.vp_home);
    }

    private void initViewPage() {
        list = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add("首页");
        titles.add("关于");
        list.add(HomeFragment.getInstance());
        list.add(AboutFragment.getInstance());
        vpHome.setAdapter(new HomePageFragmentAdapter(getSupportFragmentManager(),titles,list));
        vpHome.addOnPageChangeListener(this);
        vpHome.setCurrentItem(0);
        for(int i = 0;i>titles.size();i++){
            TabLayout.Tab tab=tabBottom.newTab();
            tab.setTag(i);
            tab.setText(titles.get(i));
            tabBottom.addTab(tab);
        }
        tabBottom.setupWithViewPager(vpHome);
    }

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void success() {

    }


    @Override
    public void fail() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLogin() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
