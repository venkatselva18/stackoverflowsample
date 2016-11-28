package realimage.stackoverflow.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import okhttp3.ResponseBody;
import realimage.stackoverflow.R;
import realimage.stackoverflow.apiService.ServiceGenerator;
import realimage.stackoverflow.apiService.ServiceInterface;
import realimage.stackoverflow.helpers.Config;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Venkatesh
 * venkatselva8@gmail.com
 */
public class LoadWebViewActivity extends AppCompatActivity {
    WebView webView;
    // ProgressBar ln_progressBar;
    Context activity_context;
    WebSettings webSettings;
    CookieManager cookieManager;
    ProgressDialog progressDialog;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        activity_context = LoadWebViewActivity.this;
        webView = (WebView) findViewById(R.id.webView);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        webView.setWebChromeClient(new MyWebViewClient());
        webSettings = webView.getSettings();

        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webView.setWebViewClient(new myWeboverideClient());

        CookieSyncManager.createInstance(this);
        cookieManager = CookieManager.getInstance();

        LoadWebsite(getIntent().getStringExtra(Config.TO_LOAD_URL));
    }

    public void LoadWebsite(String url) {
        Log.v("LN", "url " + url);
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);

    }

    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //       LoadWebViewActivity.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }

    }

    public class myWeboverideClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Snackbar.make(coordinatorLayout, "Please wait, Stackoverflow site loading", Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(Config.OAUTH_CALLBACK_URL)) {
                String code = url.substring(url.indexOf("code") + 5);
                getRequestToken(code);
            } else {
                view.loadUrl(url);
            }
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }
    }

    private void getRequestToken(final String accessCode) {
        progressDialog = ProgressDialog.show(activity_context, "Please wait", "Getting your account credentials");
        ServiceInterface serviceInterface = ServiceGenerator.createAccessTokenService(ServiceInterface.class);
        serviceInterface.getAccessToken(Config.CLIENT_ID, Config.OAUTH_CALLBACK_URL,
                Config.CONSUMER_SECRET, accessCode).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    String responseInString = response.body().string();
                    if (responseInString.startsWith("access_token")) {
                        cookieManager.setAcceptCookie(false);
                        webSettings.setSaveFormData(false);
                        webSettings.setSavePassword(false);
                        webView.clearCache(true);
                        webView.clearSslPreferences();
                        webView.clearFormData();
                        webView.clearHistory();
                        String accessToken = responseInString.substring(13, 37);
                        Config.ACCESS_CODE_FROM_WEBVIEW = accessToken.trim();
                        Config.IS_AVAILABLE_ACCESS_CODE_FROM_WEBVIEW = true;
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    LoadWebsite(getIntent().getStringExtra(Config.TO_LOAD_URL));

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity_context, "Something went wrong, try again later", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView != null) {
            webView.destroy();
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
