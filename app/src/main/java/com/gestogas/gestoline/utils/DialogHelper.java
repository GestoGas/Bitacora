package com.gestogas.gestoline.utils;

import android.app.Dialog;
import android.content.Context;

public class DialogHelper {
    private static Dialog progressDialog;


    public static void showProgressDialog(Context context) {
        if (progressDialog == null) {
            progressDialog = new FullScreenProgressDialog(context);
        }
        progressDialog.show();
    }


    public static void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null; // Liberar la referencia
        }
    }
}
