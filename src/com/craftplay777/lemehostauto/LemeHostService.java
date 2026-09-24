package com.craftplay777.lemehostauto;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.webkit.WebView;
import java.security.SecureRandom;

public class LemeHostService extends Service {

    private static final String CHANNEL_ID = "lemehost_channel";
    private static final String URL_DEFECTO_SERVIDOR = "https://lemehost.com/server/10263129/free-plan";
    private static final long INTERVALO_START = 30 * 60 * 1000 + 2 * 1000; // 30 min 2 seg

    private static final String SCRIPT_EXTEND =
            "(function(){var els=document.querySelectorAll('button, a');" +
            "for(var i=0;i<els.length;i++){" +
            "if(els[i].textContent.trim()==='Extend time'){" +
            "if(els[i].disabled){return 'disabled';}" +
            "els[i].click();return 'ok';}}" +
            "return 'not_found';})();";

    private static final String SCRIPT_START =
            "(function(){var els=document.querySelectorAll('button, a');" +
            "for(var i=0;i<els.length;i++){" +
            "if(els[i].textContent.trim()==='Start'){" +
            "if(els[i].disabled){return 'disabled';}" +
            "els[i].click();return 'ok';}}" +
            "return 'not_found';})();";

    private final SecureRandom random = new SecureRandom();

    private WebView webView;
    private PowerManager.WakeLock wakeLock;
    private Handler handler;
    private NotificationManager notificationManager;

    private int contador = 0;
    private long ultimoIntervaloMillis = -1;
    private long proximaEjecucionMillis = 0;
    private long proximaStartMillis = 0;

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

        handler = new Handler();

        SharedPreferences prefs = getSharedPreferences("lemehost_auto_prefs", MODE_PRIVATE);
        String urlServidor = prefs.getString("url_servidor", URL_DEFECTO_SERVIDOR);

        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new PageReadyWatcher(new PrimeraCargaRunnable(this, handler), handler));
        webView.loadUrl(urlServidor);

        handler.postDelayed(new NotificationTicker(this, handler), 1000);
    }

    // Función: sortea minutos (5-28) y segundos (1-60), evitando repetir un valor parecido al anterior
    private long generarIntervaloAleatorio() {
        long candidato;
        int intentos = 0;
        do {
            int minutos = 5 + random.nextInt(24);
            int segundos = 1 + random.nextInt(60);
            candidato = minutos * 60000L + segundos * 1000L;
            intentos++;
        } while (Math.abs(candidato - ultimoIntervaloMillis) < 5000 && intentos < 15);
        ultimoIntervaloMillis = candidato;
        return candidato;
    }

    // Función: presiona Extend time y programa la próxima ejecución con tiempo aleatorio
    public void ejecutarExtend() {
        webView.evaluateJavascript(SCRIPT_EXTEND, new ExtendResultCallback(this));
        contador++;
        long intervalo = generarIntervaloAleatorio();
        proximaEjecucionMillis = System.currentTimeMillis() + intervalo;
        handler.postDelayed(new ExtendRunnable(this), intervalo);
    }

    // Función: presiona Start por si el server se apagó, cada 30:02 fijo
    public void presionarStart() {
        webView.evaluateJavascript(SCRIPT_START, null);
        proximaStartMillis = System.currentTimeMillis() + INTERVALO_START;
    }

    // Función: arma el texto actual de la notificación
    public void actualizarNotificacion() {
        String texto;
        if (proximaEjecucionMillis == 0) {
            texto = "Cargando página...";
        } else {
            long restanteExtend = Math.max(0, proximaEjecucionMillis - System.currentTimeMillis());
            long minExtend = restanteExtend / 60000;
            long segExtend = (restanteExtend % 60000) / 1000;

            long restanteStart = Math.max(0, proximaStartMillis - System.currentTimeMillis());
            long minStart = restanteStart / 60000;
            long segStart = (restanteStart % 60000) / 1000;

            texto = String.format(
                    "Extend en %02d:%02d (van %d) · Start (30:02) en %02d:%02d",
                    minExtend, segExtend, contador, minStart, segStart);
        }
        notificationManager.notify(1, construirNotificacion(texto));
    }

    private Notification construirNotificacion(String texto) {
        Intent stopIntent = new Intent(this, StopActionReceiver.class);
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(
                this, 0, stopIntent, PendingIntent.FLAG_IMMUTABLE);

        return new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("LemeHost Auto")
                .setContentText(texto)
                .setStyle(new Notification.BigTextStyle().bigText(texto))
                .setSmallIcon(android.R.drawable.ic_menu_recent_history)
                .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Detener automatización", stopPendingIntent)
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
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
        if (webView != null) {
            webView.destroy();
        }
    }
}
