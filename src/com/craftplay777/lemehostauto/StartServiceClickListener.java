package com.craftplay777.lemehostauto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class StartServiceClickListener implements View.OnClickListener {

    private final Context context;

    public StartServiceClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        SharedPreferences prefs = context.getSharedPreferences("lemehost_auto_prefs", Context.MODE_PRIVATE);
        String urlServidor = prefs.getString("url_servidor", "");

        if (urlServidor.trim().isEmpty()) {
            mostrarErrorSinServidor();
            return;
        }

        Intent intent = new Intent(context, LemeHostService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
        Toast.makeText(context, "Automatización activada", Toast.LENGTH_SHORT).show();
    }

    // Función: aviso en rojo cuando no hay URL de servidor configurada
    private void mostrarErrorSinServidor() {
        TextView textoError = new TextView(context);
        textoError.setText("NO SE HA DEFINIDO UNA DIRECCIÓN DE SERVIDOR.\n\"?\" > URL del servidor al automatizar > [campo de texto]");
        textoError.setTextColor(0xFFFFFFFF);
        textoError.setBackgroundColor(0xFFCC0000);
        textoError.setPadding(32, 24, 32, 24);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(textoError);
        toast.show();
    }
}
