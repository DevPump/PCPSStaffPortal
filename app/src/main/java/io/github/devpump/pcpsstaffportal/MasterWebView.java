package io.github.devpump.pcpsstaffportal;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by devpump on 4/17/17.
 */

public class MasterWebView extends Activity {

    WebView wv_masterWebView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_webview);

        Bundle bndl = getIntent().getExtras();

        wv_masterWebView = (WebView)findViewById(R.id.wv_masterWebView);

        wv_masterWebView.getSettings().setJavaScriptEnabled(true);
        String url = "";
        try {
            String urlEncoded = URLEncoder.encode(bndl.get("url").toString(), "UTF-8");
            url = "http://docs.google.com/viewer?url=" + urlEncoded;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        wv_masterWebView.loadUrl(url);

        super.onCreate(savedInstanceState);
    }
}
