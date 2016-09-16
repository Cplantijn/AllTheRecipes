package com.raywenderlich.alltherecipes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;

public class RecipeDetailActivity extends AppCompatActivity {

    private WebView mWebView;
    private ProgressDialog mProgrDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        String title = this.getIntent().getExtras().getString("title");
        String url = this.getIntent().getExtras().getString("url");

        setTitle(title);

        mWebView = (WebView) findViewById(R.id.detail_web_view);

        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (mProgrDialog == null) {
                    mProgrDialog = new ProgressDialog(RecipeDetailActivity.this);
                    mProgrDialog.setMessage("Loading...");
                    mProgrDialog.show();
                }
                view.loadUrl(url);
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);

                if (mProgrDialog == null) {
                    mProgrDialog = new ProgressDialog(RecipeDetailActivity.this);
                    mProgrDialog.setMessage("Loading...");
                    mProgrDialog.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                try {
                    if (mProgrDialog.isShowing()) {
                        mProgrDialog.dismiss();
                        mProgrDialog = null;
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mWebView.loadUrl(url);
    }
}
