package com.example.SurveyApp.util;

import android.app.AlertDialog;
import android.content.Context;

import dmax.dialog.SpotsDialog;

public class WaitingDialog {
    private SpotsDialog.Builder builder;
    private AlertDialog spotsDialog;

    public WaitingDialog(Context context) {
        builder = new SpotsDialog.Builder();
        builder.setContext(context);
    }

    public void show(String message) {
        builder.setMessage(message);
        spotsDialog = builder.build();
        spotsDialog.show();
    }

    public void dismiss() {
        spotsDialog.dismiss();
    }
}
