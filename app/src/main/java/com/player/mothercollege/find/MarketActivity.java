package com.player.mothercollege.find;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;

/**
 * Created by Administrator on 2016/10/25.
 * 商城
 */
public class MarketActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_back;
    private TextView tv_details_title;
    private WebView webView;
    private ProgressBar bar;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_find_market);
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        webView = (WebView) findViewById(R.id.webView);
        bar = (ProgressBar) findViewById(R.id.bar);

        tv_details_title.setText("商城");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);

    }

    @Override
    public void initData() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100){
                    bar.setVisibility(View.VISIBLE);
                }else {
                    if (View.INVISIBLE == bar.getVisibility()){
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.loadUrl("http://121.42.31.133:8201/m/shop");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                if(webView.canGoBack()) {
                    webView.goBack();//返回上一页面
                } else {
                    finish();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
            {
                webView.goBack();//返回上一页面
                return true;
            }
            else
            {
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
