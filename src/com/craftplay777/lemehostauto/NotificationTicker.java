package com.craftplay777.lemehostauto;

import android.os.Handler;

public class NotificationTicker implements Runnable {

    private static final long INTERVALO_TICK = 1000; // 1 segundo

    private final LemeHostService service;
    private final Handler handler;

    public NotificationTicker(LemeHostService service, Handler handler) {
        this.service = service;
        this.handler = handler;
    }

    @Override
    public void run() {
        service.actualizarNotificacion();
        handler.postDelayed(new NotificationTicker(service, handler), INTERVALO_TICK);
    }
}
