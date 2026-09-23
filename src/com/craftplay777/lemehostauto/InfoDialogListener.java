package com.craftplay777.lemehostauto;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

public class InfoDialogListener implements View.OnClickListener {

    private final Context context;
    private final String titulo;
    private final String mensaje;

    public InfoDialogListener(Context context, String titulo, String mensaje) {
        this.context = context;
        this.titulo = titulo;
        this.mensaje = mensaje;
    }

    @Override
    public void onClick(View v) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Cerrar", null)
                .create();
        dialog.show();
        Tema.estilizarDialogo(dialog, context);
    }
}
