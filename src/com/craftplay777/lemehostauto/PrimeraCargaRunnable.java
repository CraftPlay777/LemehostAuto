package com.craftplay777.lemehostauto;

import android.os.Handler;

public class PrimeraCargaRunnable implements Runnable {

    private final LemeHostService service;
    private final Handler handler;

    public PrimeraCargaRunnable(LemeHostService service, Handler handler) {
        this.service = service;
        this.handler = handler;
    }

    @Override
    public void run() {
        service.ejecutarExtend();
        new StartCheckRunnable(service, handler).run();
    }
}
