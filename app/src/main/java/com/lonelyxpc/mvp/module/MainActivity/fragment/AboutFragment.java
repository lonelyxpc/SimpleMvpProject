package com.lonelyxpc.mvp.module.MainActivity.fragment;

import com.lonelyxpc.mvp.R;
import com.lonelyxpc.mvp.base.BaseFragment;
import com.lonelyxpc.mvp.base.BasePresenter;
import com.lonelyxpc.mvp.module.MainActivity.presenter.MainActivityPresenter;

public class AboutFragment extends BaseFragment {

    private static AboutFragment mHomeInstence;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_about;
    }

    @Override
    public MainActivityPresenter initPresenter() {
        return new MainActivityPresenter();
    }

    @Override
    public void initData() {

    }

    @Override
    public void InitView() {

    }

    @Override
    public void onClick(int id) {

    }

    public static AboutFragment getInstance() {

        if (mHomeInstence== null)  {
            synchronized (AboutFragment.class) {
                if (mHomeInstence== null)  {
                    mHomeInstence= new AboutFragment();
                }
            }
        }
        return mHomeInstence;
    }
}
