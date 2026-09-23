package com.craftplay777.lemehostauto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Tema {

    private static final int FONDO_OSCURO = 0xFF121212;
    private static final int TEXTO_OSCURO = 0xFFEDEDED;
    private static final int CAMPO_OSCURO = 0xFF1E1E1E;
    private static final int BOTON_OSCURO = 0xFF2A2A2A;

    private static final int FONDO_CLARO = 0xFFF5F5F5;
    private static final int TEXTO_CLARO = 0xFF1A1A1A;
    private static final int CAMPO_CLARO = 0xFFFFFFFF;
    private static final int BOTON_CLARO = 0xFFE0E0E0;

    private static Typeface fuente;

    public static boolean esOscuro(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("lemehost_auto_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("modo_oscuro", true);
    }

    // Función: carga la tipografía una sola vez desde assets/fonts/serif.ttf
    public static Typeface obtenerFuente(Context context) {
        if (fuente == null) {
            try {
                fuente = Typeface.createFromAsset(context.getAssets(), "fonts/serif.ttf");
            } catch (RuntimeException e) {
                fuente = Typeface.DEFAULT;
            }
        }
        return fuente;
    }

    // Función: aplica colores, tipografía y esquinas redondeadas a una vista y a todos sus hijos
    public static void aplicar(View vista, Context context) {
        boolean oscuro = esOscuro(context);
        Typeface tipografia = obtenerFuente(context);

        vista.setBackgroundColor(oscuro ? FONDO_OSCURO : FONDO_CLARO);
        aplicarRecursivo(vista, tipografia, oscuro);
    }

    private static void aplicarRecursivo(View vista, Typeface tipografia, boolean oscuro) {
        if (vista instanceof WebView) {
            return;
        }

        int colorTexto = oscuro ? TEXTO_OSCURO : TEXTO_CLARO;

        if (vista instanceof EditText) {
            EditText campo = (EditText) vista;
            campo.setTypeface(tipografia);
            campo.setTextColor(colorTexto);
            campo.setBackground(crearFondoRedondeado(oscuro ? CAMPO_OSCURO : CAMPO_CLARO));
            campo.setPadding(24, 16, 24, 16);
        } else if (vista instanceof Button) {
            Button boton = (Button) vista;
            boton.setTypeface(tipografia);
            boton.setTextColor(colorTexto);
            boton.setBackground(crearFondoRedondeado(oscuro ? BOTON_OSCURO : BOTON_CLARO));
        } else if (vista instanceof TextView) {
            TextView texto = (TextView) vista;
            texto.setTypeface(tipografia);
            texto.setTextColor(colorTexto);
        }

        if (vista instanceof ViewGroup) {
            ViewGroup grupo = (ViewGroup) vista;
            for (int i = 0; i < grupo.getChildCount(); i++) {
                aplicarRecursivo(grupo.getChildAt(i), tipografia, oscuro);
            }
        }
    }

    private static GradientDrawable crearFondoRedondeado(int color) {
        GradientDrawable fondo = new GradientDrawable();
        fondo.setColor(color);
        fondo.setCornerRadius(24f);
        return fondo;
    }

    // Función: le da esquinas redondeadas y el color de fondo del tema a un diálogo
    public static void estilizarDialogo(AlertDialog dialog, Context context) {
        Window ventana = dialog.getWindow();
        if (ventana == null) {
            return;
        }
        boolean oscuro = esOscuro(context);
        GradientDrawable fondo = new GradientDrawable();
        fondo.setColor(oscuro ? FONDO_OSCURO : FONDO_CLARO);
        fondo.setCornerRadius(32f);
        ventana.setBackgroundDrawable(fondo);
    }
}
