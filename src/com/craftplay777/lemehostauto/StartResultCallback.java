package com.craftplay777.lemehostauto;

import android.content.Context;
import android.webkit.ValueCallback;
import android.widget.Toast;

public class StartResultCallback implements ValueCallback<String> {

    private final Context context;

    public StartResultCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onReceiveValue(String value) {
        Toast.makeText(context, "Resultado Start: " + value, Toast.LENGTH_LONG).show();
    }
}
