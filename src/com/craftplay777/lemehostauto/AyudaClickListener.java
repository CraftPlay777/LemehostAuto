package com.craftplay777.lemehostauto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class AyudaClickListener implements View.OnClickListener {

    private static final String INFO_SERVIDOR =
            "Aquí se coloca la URL de la dirección exacta de tu servidor.\n" +
            "Para conseguirla:\n" +
            "• Entra a tu navegador\n" +
            "• Ve a https://lemehost.com/server/index\n" +
            "• Entra a tu servidor\n" +
            "• Copia la URL de esa página\n" +
            "• Entra aquí y pega esa URL\n" +
            "• Y listo";

    private static final String INFO_DEFECTO =
            "Aquí elegís dónde querés aparecer al entrar a la app.\n" +
            "Podés colocar simplemente \"https://lemehost.com/\"\n" +
            "O la dirección de tu servidor directo, ej:\n" +
            "https://lemehost.com/server/TU_ID/free-plan\n" +
            "O cualquier otra subdirección de lemehost.com";

    private final Context context;
    private final WebView webView;

    public AyudaClickListener(Context context, WebView webView) {
        this.context = context;
        this.webView = webView;
    }

    @Override
    public void onClick(View v) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vista = inflater.inflate(R.layout.dialog_ayuda, null);

        SharedPreferences prefs = context.getSharedPreferences("lemehost_auto_prefs", Context.MODE_PRIVATE);

        EditText editUrlServidor = (EditText) vista.findViewById(R.id.editUrlServidor);
        String urlServidorGuardada = prefs.getString("url_servidor", "");
        if (!urlServidorGuardada.isEmpty()) {
            editUrlServidor.setText(urlServidorGuardada);
        }

        EditText editUrlDefecto = (EditText) vista.findViewById(R.id.editUrlDefecto);
        editUrlDefecto.setText(prefs.getString("url_defecto", "https://lemehost.com/server/index"));

        Switch switchModoOscuro = (Switch) vista.findViewById(R.id.switchModoOscuro);
        switchModoOscuro.setChecked(Tema.esOscuro(context));
        switchModoOscuro.setOnCheckedChangeListener(new ModoOscuroListener(context));

        Tema.aplicar(vista, context);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(vista)
                .create();

        Button btnGuardar = (Button) vista.findViewById(R.id.btnGuardarUrl);
        btnGuardar.setOnClickListener(new GuardarUrlListener(context, editUrlDefecto, editUrlServidor, webView, dialog));

        Button btnCerrar = (Button) vista.findViewById(R.id.btnCerrarAyuda);
        btnCerrar.setOnClickListener(new CerrarDialogListener(dialog));

        Button btnInfoServidor = (Button) vista.findViewById(R.id.btnInfoServidor);
        btnInfoServidor.setOnClickListener(new InfoDialogListener(context, "URL del servidor", INFO_SERVIDOR));

        Button btnInfoDefecto = (Button) vista.findViewById(R.id.btnInfoDefecto);
        btnInfoDefecto.setOnClickListener(new InfoDialogListener(context, "URL por defecto", INFO_DEFECTO));

        TextView linkGithub = (TextView) vista.findViewById(R.id.linkGithub);
        linkGithub.setOnClickListener(new OpenLinkListener(context, "https://github.com/CraftPlay777/"));

        TextView linkYoutube = (TextView) vista.findViewById(R.id.linkYoutube);
        linkYoutube.setOnClickListener(new OpenLinkListener(context, "https://youtube.com/@themtlua/"));

        TextView linkDiscord = (TextView) vista.findViewById(R.id.linkDiscord);
        linkDiscord.setOnClickListener(new OpenLinkListener(context, "https://discord.gg/6T7EyPXd"));

        TextView linkRepo = (TextView) vista.findViewById(R.id.linkRepo);
        linkRepo.setOnClickListener(new OpenLinkListener(context, "https://github.com/CraftPlay777/LemehostAuto/"));

        dialog.show();
        Tema.estilizarDialogo(dialog, context);
    }
}
