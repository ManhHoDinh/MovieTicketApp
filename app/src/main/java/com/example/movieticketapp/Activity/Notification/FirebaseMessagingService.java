package com.example.movieticketapp.Activity.Notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.example.movieticketapp.R;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

     NotificationManager mNotificationManager;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


// playing audio and vibration when user se reques
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            r.setLooping(false);
        }

        // vibration
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100, 300, 300, 300};
//        v.vibrate(pattern, -1);


        int resourceImage = getResources().getIdentifier(remoteMessage.getNotification().getIcon(), "drawable", getPackageName());

        RemoteViews collapsedView = new RemoteViews(getPackageName(),R.layout.notification_collapsed);
        RemoteViews expandedView = new RemoteViews(getPackageName(),R.layout.notification_expanded);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.drawable.notification_icon);
        } else {
            builder.setSmallIcon(R.drawable.notification_icon);
        }


        collapsedView.setTextViewText(R.id.titleCollapsed, remoteMessage.getNotification().getTitle());
        collapsedView.setTextViewText(R.id.contentCollapsed, remoteMessage.getNotification().getBody());

        expandedView.setTextViewText(R.id.titleExpanded, remoteMessage.getNotification().getTitle());
        expandedView.setTextViewText(R.id.contentExpanded, remoteMessage.getNotification().getBody());
        builder.setSmallIcon(R.drawable.notification_icon);
        builder.setContentTitle(remoteMessage.getNotification().getTitle());
        builder.setCustomContentView(collapsedView);
        builder.setCustomBigContentView(expandedView);
        builder.setContentText(remoteMessage.getNotification().getBody());
//        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()));
        builder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        builder.setAutoCancel(false);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setVibrate(new long[]{100,1000,200,340});
        mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }



// notificationId is a unique int for each notification that you must define
        mNotificationManager.notify(100, builder.build());


    }

}


