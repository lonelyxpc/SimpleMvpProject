package com.lonelyxpc.mvp.module.MainActivity.fragment;

import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;

import com.lonelyxpc.mvp.R;
import com.lonelyxpc.mvp.base.BaseFragment;
import com.lonelyxpc.mvp.module.MainActivity.presenter.MainActivityPresenter;
import com.lonelyxpc.mvp.module.WebViewActivity.view.WebViewActivity;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {

    private static HomeFragment mHomeInstence;
    @BindView(R.id.swrl_refresh)
    SwipeRefreshLayout swrlRefresh;
    @BindView(R.id.tv_web)
    TextView tvWeb;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_home;
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

        swrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swrlRefresh.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }

    @Override
    public void onClick(int id) {

    }

    @OnClick({R.id.tv_web})
    public void web(){
        Intent intent = new Intent(context, WebViewActivity.class);
        startActivity(intent);
    }

    public static HomeFragment getInstance() {

        if (mHomeInstence == null) {
            synchronized (HomeFragment.class) {
                if (mHomeInstence == null) {
                    mHomeInstence = new HomeFragment();
                }
            }
        }
        return mHomeInstence;
    }
}
