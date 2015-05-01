package edu.sjsu.writingcenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.security.KeyException;


public class AppointmentActivity extends ActionBarActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        setTitle("Appointments");
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.loadUrl(getString(R.string.mobile_appointment_url));
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        mWebView.setWebViewClient(new WebViewClient());

        final ActionBarActivity activity = this;
        mWebView.setWebChromeClient(new WebChromeClient()
        {
            public void onProgressChanged(WebView view, int progress)
            {
                activity.setTitle("Loading...");
                activity.setProgress(progress * 100);

                if(progress == 100)
                    activity.setTitle("Appointments");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_appointment, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mWebView != null) {
            if(mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                if(mWebView != null) {
                    if(mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    } else {
                        return super.onOptionsItemSelected(item);
                    }
                } else {
                    return super.onOptionsItemSelected(item);
                }
            case R.id.refresh:
                if(mWebView != null) {
                    mWebView.reload();
                    return true;
                }
                break;
            case R.id.external_browser:
                if(mWebView != null) {
                    Intent openInExternalBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(mWebView.getUrl()));
                    startActivity(openInExternalBrowser);
                    return true;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
