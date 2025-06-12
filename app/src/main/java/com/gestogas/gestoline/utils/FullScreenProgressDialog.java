package com.gestogas.gestoline.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.gestogas.gestoline.R;

public class FullScreenProgressDialog extends Dialog {
    public FullScreenProgressDialog(Context context) {
        super(context);
        // Configurar el diálogo
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fullscreen_progress_dialog);
        setCancelable(false);

        if (getWindow() != null) {
            getWindow().setLayout(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
            );
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }
}
