package com.craftplay777.lemehostauto;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginRedirectWatcher extends WebViewClient {

    private String urlAnterior = "";

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        boolean veniaDeLogin = urlAnterior.contains("/site/login") || urlAnterior.contains("/site/signup");
        boolean estaEnLoginAhora = url.contains("/site/login") || url.contains("/site/signup");

        if (veniaDeLogin && !estaEnLoginAhora && !url.contains("/server/index")) {
            urlAnterior = url;
            view.loadUrl("https://lemehost.com/server/index");
            return;
        }

        urlAnterior = url;
    }
}
