package com.craftplay777.lemehostauto;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

public class PrimeraVezDialogListener implements DialogInterface.OnClickListener {

    private final Context context;

    public PrimeraVezDialogListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        SharedPreferences prefs = context.getSharedPreferences("lemehost_auto_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("primera_vez_mostrada", true).apply();
        dialog.dismiss();
    }
}
