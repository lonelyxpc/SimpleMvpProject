package com.lonelyxpc.mvp.module.KeepActivity.view;

import android.os.Bundle;
import android.util.Log;

import com.lonelyxpc.mvp.base.BaseActivity;
import com.lonelyxpc.mvp.module.KeepActivity.presenter.KeepActivityPresenter;

public class KeepActivity extends BaseActivity<IKeepActivityView, KeepActivityPresenter> implements IKeepActivityView {


    @Override
    public KeepActivityPresenter initPresenter() {
        return new KeepActivityPresenter();
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
