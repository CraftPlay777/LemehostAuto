package com.craftplay777.lemehostauto;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.CompoundButton;

public class ModoOscuroListener implements CompoundButton.OnCheckedChangeListener {

    private final Context context;

    public ModoOscuroListener(Context context) {
        this.context = context;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences prefs = context.getSharedPreferences("lemehost_auto_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("modo_oscuro", isChecked).apply();

        if (context instanceof Activity) {
            Activity actividad = (Activity) context;
            Tema.aplicar(actividad.getWindow().getDecorView(), context);
        }
    }
}
