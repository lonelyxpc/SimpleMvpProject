package com.lonelyxpc.mvp.module.WebViewActivity.view;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.AgentWebUtils;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.lonelyxpc.mvp.R;
import com.lonelyxpc.mvp.base.BaseActivity;
import com.lonelyxpc.mvp.module.MainActivity.view.MainActivity;
import com.lonelyxpc.mvp.module.WebViewActivity.presenter.WebViewActivityPresenter;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity<IWebViewActivityView, WebViewActivityPresenter> implements IWebViewActivityView {


    @BindView(R.id.ll_webview)
    LinearLayout llWebview;
    @BindView(R.id.swrl_refresh)
    SwipeRefreshLayout swrlRefresh;
    private AgentWeb mAgentWeb;

    @Override
    public WebViewActivityPresenter initPresenter() {
        return new WebViewActivityPresenter();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        initWebView();
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

    public void initWebView() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(llWebview, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setAgentWebWebSettings(getSettings())
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(getUrl());
        mAgentWeb.getJsInterfaceHolder().addJavaObject("JSApi", presenter);
    }

    private String getUrl(){
        return "https://www.baidu.com";
    }

    public IAgentWebSettings getSettings() {
        return new AbsAgentWebSettings() {
            private AgentWeb mAgentWeb;

            @Override
            protected void bindAgentWebSupport(AgentWeb agentWeb) {
                this.mAgentWeb = agentWeb;
            }

            /**
             * AgentWeb 4.0.0 内部删除了 DownloadListener 监听 ，以及相关API ，将 Download 部分完全抽离出来独立一个库，
             * 如果你需要使用 AgentWeb Download 部分 ， 请依赖上 compile 'com.just.agentweb:download:4.0.0 ，
             * 如果你需要监听下载结果，请自定义 AgentWebSetting ， New 出 DefaultDownloadImpl，传入DownloadListenerAdapter
             * 实现进度或者结果监听，例如下面这个例子，如果你不需要监听进度，或者下载结果，下面 setDownloader 的例子可以忽略。
             * @param webView
             * @param
             * @return WebListenerManager
             */

            @Override
            public IAgentWebSettings toSetting(WebView webView) {
                webView.resumeTimers();
                WebSettings mWebSettings = webView.getSettings();
                mWebSettings.setMediaPlaybackRequiresUserGesture(false);
                mWebSettings.setUseWideViewPort(true);//自适应屏幕
                mWebSettings.setJavaScriptEnabled(true);//扩大比例的缩放
                mWebSettings.setDomStorageEnabled(true);
                mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
                    mWebSettings.setLoadWithOverviewMode(true);
                }
                mWebSettings.setSupportZoom(true);
                mWebSettings.setBuiltInZoomControls(false);
                mWebSettings.setSavePassword(false);
                if (AgentWebUtils.checkNetwork(webView.getContext())) {
                    //根据cache-control获取数据。
                    mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
                } else {
                    //没网，则从本地获取，即离线加载
                    mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //适配5.0不允许http和https混合使用情况
                    mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                    webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                }
                mWebSettings.setTextZoom(100);
                mWebSettings.setDatabaseEnabled(true);
                mWebSettings.setAppCacheEnabled(true);
                mWebSettings.setLoadsImagesAutomatically(true);
                mWebSettings.setSupportMultipleWindows(false);
                // 是否阻塞加载网络图片  协议http or https
                mWebSettings.setBlockNetworkImage(false);
                // 允许加载本地文件html  file协议
                mWebSettings.setAllowFileAccess(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    // 通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
                    mWebSettings.setAllowFileAccessFromFileURLs(false);
                    // 允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
                    mWebSettings.setAllowUniversalAccessFromFileURLs(false);
                }
                mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                } else {
                    mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
                }
                mWebSettings.setDomStorageEnabled(true);
                mWebSettings.setNeedInitialFocus(true);
                mWebSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
                mWebSettings.setDefaultFontSize(16);
                mWebSettings.setMinimumFontSize(12);//设置 WebView 支持的最小字体大小，默认为 8
                mWebSettings.setGeolocationEnabled(true);
                String dir = AgentWebConfig.getCachePath(webView.getContext());
                //设置数据库路径  api19 已经废弃,这里只针对 webkit 起作用
                mWebSettings.setGeolocationDatabasePath(dir);
                mWebSettings.setDatabasePath(dir);
                mWebSettings.setAppCachePath(dir);
                //缓存文件最大值
                mWebSettings.setAppCacheMaxSize(Long.MAX_VALUE);
                return this;
            }
        };
    }
    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_web_view);
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.e("console", "[" + consoleMessage.messageLevel() + "] " + consoleMessage.message() + "(" + consoleMessage.sourceId() + ":" + consoleMessage.lineNumber() + ")");
            return super.onConsoleMessage(consoleMessage);
        }
    };
    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
            Log.d("onReceivedSslError", "onReceivedSslError: "); //如果是证书问题，会打印出此条log到console
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            Log.e("H5url", String.valueOf(request.getUrl()));
            return super.shouldInterceptRequest(view, request);
        }
    };

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
