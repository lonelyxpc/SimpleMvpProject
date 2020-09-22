package com.lonelyxpc.mvp.module.Main2Activity.view;

import android.os.Bundle;

import com.lonelyxpc.mvp.base.BaseActivity;
import com.lonelyxpc.mvp.module.Main2Activity.presenter.Main2ActivityPresenter;

public class Main2Activity extends BaseActivity<IMain2ActivityView, Main2ActivityPresenter> implements IMain2ActivityView {


    @Override
    public Main2ActivityPresenter initPresenter() {
        return new Main2ActivityPresenter();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {

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
}
