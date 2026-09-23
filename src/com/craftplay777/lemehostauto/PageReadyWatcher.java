package com.craftplay777.lemehostauto;

import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PageReadyWatcher extends WebViewClient {

    private static final long RETRASO_INICIAL = 5000; // 5 segundos, para que el estado real llegue por WebSocket

    private final Runnable accionInicial;
    private final Handler handler;
    private boolean yaEjecutado = false;

    public PageReadyWatcher(Runnable accionInicial, Handler handler) {
        this.accionInicial = accionInicial;
        this.handler = handler;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (!yaEjecutado) {
            yaEjecutado = true;
            handler.postDelayed(accionInicial, RETRASO_INICIAL);
        }
    }
}
