package com.craftplay777.lemehostauto;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

public class StartServiceClickListener implements View.OnClickListener {

    private final Context context;

    public StartServiceClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, LemeHostService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
        Toast.makeText(context, "Automatización activada", Toast.LENGTH_SHORT).show();
    }
}
