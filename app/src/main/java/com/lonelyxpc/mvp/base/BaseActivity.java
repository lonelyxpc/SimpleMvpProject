package com.lonelyxpc.mvp.base;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.lonelyxpc.mvp.R;
import com.lonelyxpc.mvp.application.MyApplication;
import com.lonelyxpc.mvp.utils.ActivityKeepManagerUtil;
import com.lonelyxpc.mvp.utils.PhoneUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {

    public abstract T initPresenter();

    public abstract void initData();

    public abstract void initView();

    public T presenter;

    protected abstract void setLayoutView(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setLayoutView(savedInstanceState);
        ButterKnife.bind(this);
        presenter = initPresenter();
        presenter.attach((V) this);
        initView();
        initData();
    }
    /*
    * 透明状态栏
    * viewid  透明状态栏所遮掩的第一个view的ID
    * */
    public void immersiveTop(int viewid) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            findViewById(viewid).setPadding(0, PhoneUtil.getStatusBarHeight(this), 0, 0);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        presenter.dettach();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if(!ActivityKeepManagerUtil.getInstance().getIsback()){
//            ActivityKeepManagerUtil.getInstance().registerKeepReceiver(this);
//        }
    }
}
