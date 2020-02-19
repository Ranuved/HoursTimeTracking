package com.works.admin.hours_timetracking;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Webview_fassai extends Activity {
    WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        Bundle b = getIntent().getExtras();
        String type = b.getString("type");
        webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (type.equalsIgnoreCase("1")) {
            webview.loadUrl("https://app.hours.fi/password/reset");
        } else {
            webview.loadUrl("https://hours.fi");
        }

    }
}
