package com.craftplay777.lemehostauto;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.webkit.WebView;
import java.util.Random;

public class LemeHostService extends Service {

    private static final String CHANNEL_ID = "lemehost_channel";
    private static final String URL = "https://lemehost.com/server/10263129/free-plan";
    private static final String SCRIPT =
            "(function(){var els=document.querySelectorAll('button, a');" +
            "for(var i=0;i<els.length;i++){" +
            "if(els[i].textContent.trim()==='Extend time'){els[i].click();return 'ok';}}" +
            "return 'not_found';})();";

    private final Random random = new Random();

    private WebView webView;
    private PowerManager.WakeLock wakeLock;
    private Handler handler;
    private NotificationManager notificationManager;

    private int contador = 0;
    private long proximaEjecucionMillis = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "LemeHost Auto", NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }

        startForeground(1, construirNotificacion("Cargando página..."));

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "LemeHostAuto::WakeLock");
        wakeLock.acquire();

        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new PageReadyWatcher(this));
        webView.loadUrl(URL);

        handler = new Handler();
        handler.postDelayed(new NotificationTicker(this, handler), 1000);
    }

    // Función: sortea un intervalo entre 8 y 27 minutos
    private long generarIntervaloAleatorio() {
        int minutos = 8 + random.nextInt(20);
        return minutos * 60 * 1000;
    }

    // Función: presiona Extend time y programa la próxima ejecución (tiempo aleatorio)
    public void ejecutarExtend() {
        webView.evaluateJavascript(SCRIPT, null);
        contador++;
        long intervalo = generarIntervaloAleatorio();
        proximaEjecucionMillis = System.currentTimeMillis() + intervalo;
        handler.postDelayed(new ExtendRunnable(this), intervalo);
    }

    // Función: arma el texto actual de la notificación
    public void actualizarNotificacion() {
        String texto;
        if (proximaEjecucionMillis == 0) {
            texto = "Cargando página...";
        } else {
            long restante = proximaEjecucionMillis - System.currentTimeMillis();
            if (restante < 0) {
                restante = 0;
            }
            long minutos = restante / 60000;
            long segundos = (restante % 60000) / 1000;
            texto = String.format("Próxima en %02d:%02d · Van %d", minutos, segundos, contador);
        }
        notificationManager.notify(1, construirNotificacion(texto));
    }

    private Notification construirNotificacion(String texto) {
        return new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("LemeHost Auto")
                .setContentText(texto)
                .setSmallIcon(android.R.drawable.ic_menu_recent_history)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
        if (webView != null) {
            webView.destroy();
        }
    }
}
