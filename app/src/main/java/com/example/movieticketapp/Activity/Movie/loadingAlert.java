package com.example.movieticketapp.Activity.Movie;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.movieticketapp.R;

public class loadingAlert {
    private Activity activity;
    private AlertDialog alertDialog;
    public  loadingAlert(Activity activity )
    {
        this.activity = activity;
    }
    void StartAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
    void closeLoadingAlert()
    {
        if (alertDialog!=null)
            alertDialog.dismiss();
    }
}
