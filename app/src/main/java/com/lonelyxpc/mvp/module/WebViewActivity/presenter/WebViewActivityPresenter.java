package com.lonelyxpc.mvp.module.WebViewActivity.presenter;


import android.webkit.JavascriptInterface;

import com.lonelyxpc.mvp.base.BasePresenter;
import com.lonelyxpc.mvp.module.WebViewActivity.view.IWebViewActivityView;

public class WebViewActivityPresenter extends BasePresenter<IWebViewActivityView> {

    @JavascriptInterface
    public void initData() {

    }

}
