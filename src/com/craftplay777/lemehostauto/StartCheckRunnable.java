package com.craftplay777.lemehostauto;

import android.os.Handler;

public class StartCheckRunnable implements Runnable {

    private static final long INTERVALO_START = 30 * 60 * 1000 + 2 * 1000; // 30 minutos y 2 segundos

    private final LemeHostService service;
    private final Handler handler;

    public StartCheckRunnable(LemeHostService service, Handler handler) {
        this.service = service;
        this.handler = handler;
    }

    @Override
    public void run() {
        service.presionarStart();
        handler.postDelayed(new StartCheckRunnable(service, handler), INTERVALO_START);
    }
}
