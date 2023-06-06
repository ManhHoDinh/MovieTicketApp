package com.example.movieticketapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatButton;

import com.example.movieticketapp.Activity.Account.SignInActivity;

public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Common.isConnectedToInternet(context)) {
            Dialog layout_dialog = new Dialog(context);
            layout_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            layout_dialog.setContentView(R.layout.check_internet_dialog);
            Window window = layout_dialog.getWindow();
            if (window != null) {
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams windowAttribute = window.getAttributes();
                windowAttribute.gravity = Gravity.CENTER;
                window.setAttributes(windowAttribute);
                layout_dialog.show();
                Button retryBtn = layout_dialog.findViewById(R.id.retryBtn);
                retryBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        layout_dialog.dismiss();
                        onReceive(context, intent);
                    }
                });


            }
        }
    }
}
