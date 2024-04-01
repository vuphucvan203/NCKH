package com.example.nghiencuukhoahoc.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.nghiencuukhoahoc.MainActivity;
import com.example.nghiencuukhoahoc.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG=MyFirebaseMessagingService.class.getName();
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        RemoteMessage.Notification notification = message.getNotification();
        if(notification==null) return;
        String strTitle=notification.getTitle();
        String strMessage=notification.getBody();

        sendNotification(strTitle,strMessage);
    }

    private void sendNotification(String strTitle, String strMessage) {
        Intent intent=new Intent(this, MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this,
                MainActivity.CHANEL_ID).setContentTitle(strTitle).
                setContentText(strMessage).setSmallIcon(R.drawable.home).setContentIntent(pendingIntent);

        Notification notification =notificationBuilder.build();
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager!=null)
        {
            notificationManager.notify(1,notification);
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.e(TAG,token);
    }
}
