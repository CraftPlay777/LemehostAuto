package com.craftplay777.lemehostauto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

public class GuardarUrlListener implements View.OnClickListener {

    private final Context context;
    private final EditText editUrlApertura;
    private final EditText editUrlServidor;
    private final WebView webView;
    private final AlertDialog dialog;

    public GuardarUrlListener(Context context, EditText editUrlApertura, EditText editUrlServidor, WebView webView, AlertDialog dialog) {
        this.context = context;
        this.editUrlApertura = editUrlApertura;
        this.editUrlServidor = editUrlServidor;
        this.webView = webView;
        this.dialog = dialog;
    }

    @Override
    public void onClick(View v) {
        String urlApertura = editUrlApertura.getText().toString().trim();
        String urlServidor = editUrlServidor.getText().toString().trim();

        if (!urlApertura.contains("lemehost.com")) {
            Toast.makeText(context, "La URL de apertura debe contener lemehost.com", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!urlServidor.contains("lemehost.com")) {
            Toast.makeText(context, "La URL del servidor debe contener lemehost.com", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = context.getSharedPreferences("lemehost_auto_prefs", Context.MODE_PRIVATE);
        prefs.edit()
                .putString("url_defecto", urlApertura)
                .putString("url_servidor", urlServidor)
                .apply();

        webView.loadUrl(urlApertura);
        Toast.makeText(context, "URLs guardadas", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }
}
