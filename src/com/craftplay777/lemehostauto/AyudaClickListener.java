package com.craftplay777.lemehostauto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AyudaClickListener implements View.OnClickListener {

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

        EditText editUrl = (EditText) vista.findViewById(R.id.editUrlDefecto);
        SharedPreferences prefs = context.getSharedPreferences("lemehost_auto_prefs", Context.MODE_PRIVATE);
        editUrl.setText(prefs.getString("url_defecto", "https://lemehost.com/server/index"));

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(vista)
                .create();

        Button btnGuardar = (Button) vista.findViewById(R.id.btnGuardarUrl);
        btnGuardar.setOnClickListener(new GuardarUrlListener(context, editUrl, webView, dialog));

        Button btnCerrar = (Button) vista.findViewById(R.id.btnCerrarAyuda);
        btnCerrar.setOnClickListener(new CerrarDialogListener(dialog));

        TextView linkGithub = (TextView) vista.findViewById(R.id.linkGithub);
        linkGithub.setOnClickListener(new OpenLinkListener(context, "https://github.com/CraftPlay777/"));

        TextView linkYoutube = (TextView) vista.findViewById(R.id.linkYoutube);
        linkYoutube.setOnClickListener(new OpenLinkListener(context, "https://youtube.com/@themtlua/"));

        TextView linkDiscord = (TextView) vista.findViewById(R.id.linkDiscord);
        linkDiscord.setOnClickListener(new OpenLinkListener(context, "https://discord.gg/6T7EyPXd"));

        TextView linkRepo = (TextView) vista.findViewById(R.id.linkRepo);
        linkRepo.setOnClickListener(new OpenLinkListener(context, "https://github.com/CraftPlay777/LemehostAuto/"));

        dialog.show();
    }
}
