package com.craftplay777.lemehostauto;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class OpenLinkListener implements View.OnClickListener {

    private final Context context;
    private final String url;

    public OpenLinkListener(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }
}
