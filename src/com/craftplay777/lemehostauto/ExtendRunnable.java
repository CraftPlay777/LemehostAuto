package com.craftplay777.lemehostauto;

public class ExtendRunnable implements Runnable {

    private final LemeHostService service;

    public ExtendRunnable(LemeHostService service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.ejecutarExtend();
    }
}
