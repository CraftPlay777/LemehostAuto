package com.craftplay777.lemehostauto;

import android.content.Context;
import android.webkit.ValueCallback;
import android.widget.Toast;

public class ExtendResultCallback implements ValueCallback<String> {

    private final Context context;

    public ExtendResultCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onReceiveValue(String value) {
        Toast.makeText(context, "Resultado Extend: " + value, Toast.LENGTH_LONG).show();
    }
}
