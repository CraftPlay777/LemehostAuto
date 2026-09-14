package com.craftplay777.lemehostauto;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PageReadyWatcher extends WebViewClient {

    private final LemeHostService service;
    private boolean yaEjecutado = false;

    public PageReadyWatcher(LemeHostService service) {
        this.service = service;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (!yaEjecutado) {
            yaEjecutado = true;
            service.ejecutarExtend();
        }
    }
}
