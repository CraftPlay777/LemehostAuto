package com.craftplay777.lemehostauto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("lemehost_auto_prefs", MODE_PRIVATE);

        if (!prefs.getBoolean("primera_vez_mostrada", false)) {
            new AlertDialog.Builder(this)
                    .setMessage("Por favor asegúrate de unirse sesión antes de presionar el botón")
                    .setCancelable(false)
                    .setPositiveButton("ACEPTAR", new PrimeraVezDialogListener(this))
                    .show();
        }

        String urlInicial = prefs.getString("url_defecto", "https://lemehost.com/server/index");

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new LoginRedirectWatcher());
        webView.loadUrl(urlInicial);

        Button btnActivar = (Button) findViewById(R.id.btnActivar);
        btnActivar.setOnClickListener(new StartServiceClickListener(this));

        Button btnAyuda = (Button) findViewById(R.id.btnAyuda);
        btnAyuda.setOnClickListener(new AyudaClickListener(this, webView));
    }
}
