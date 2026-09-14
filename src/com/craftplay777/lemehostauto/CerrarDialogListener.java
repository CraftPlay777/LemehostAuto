package com.craftplay777.lemehostauto;

import android.app.AlertDialog;
import android.view.View;

public class CerrarDialogListener implements View.OnClickListener {

    private final AlertDialog dialog;

    public CerrarDialogListener(AlertDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void onClick(View v) {
        dialog.dismiss();
    }
}
