package com.craftplay777.lemehostauto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class StopActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.stopService(new Intent(context, LemeHostService.class));
        Toast.makeText(context, "Automatización detenida", Toast.LENGTH_SHORT).show();
    }
}
