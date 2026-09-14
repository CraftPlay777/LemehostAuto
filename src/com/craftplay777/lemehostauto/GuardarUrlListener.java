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
    private final EditText editUrl;
    private final WebView webView;
    private final AlertDialog dialog;

    public GuardarUrlListener(Context context, EditText editUrl, WebView webView, AlertDialog dialog) {
        this.context = context;
        this.editUrl = editUrl;
        this.webView = webView;
        this.dialog = dialog;
    }

    @Override
    public void onClick(View v) {
        String url = editUrl.getText().toString().trim();

        if (!url.contains("lemehost.com")) {
            Toast.makeText(context, "La URL debe contener lemehost.com", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = context.getSharedPreferences("lemehost_auto_prefs", Context.MODE_PRIVATE);
        prefs.edit().putString("url_defecto", url).apply();

        webView.loadUrl(url);
        Toast.makeText(context, "URL guardada", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }
}
