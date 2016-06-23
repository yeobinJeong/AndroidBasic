package com.example.servicedemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Date;

public class MyService extends Service {
    public MyService() {
    }


    //서비스가 생성될 때 호출되는 메서드
    @Override
    public void onCreate() {

        System.out.println("onServiceCreate ---");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private boolean running;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
          //start시 호출되는 메소드

        System.out.println("onStartCommand ---");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyService.this);

        builder.setTicker("New Message Received");
        builder.setContentTitle("Message Received");
        builder.setContentText("Click here to check received message");
        builder.setSmallIcon(R.mipmap.ic_launcher);

        builder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        builder.setAutoCancel(true);

        Intent notificationIntent = new Intent(getApplicationContext(), NotificationResultActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();

        startForeground(1, notification);

        startForeground(0, null);

        (new Thread() {
            public void run() {
                long prev = 0;
                running = true;

                while (running) {
                    long current = new Date().getTime();
                    if (current - prev > 5000) {
                        prev = current;
                        Intent intent2 = new Intent();
                        intent2.putExtra("number", (int) (current / 1000));
                        intent2.setAction("com.example.broadcast.MYMESSAGE");
                        sendBroadcast(intent2);
                        Log.e("MyService", "sendMessage");
                        //sendNotification();
                    } else {
                        yield();
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setTicker("New Message Received");
        builder.setContentTitle("Message Received");
        builder.setContentText("Click here to check received message");
        builder.setSmallIcon(R.mipmap.ic_launcher);

        builder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        builder.setAutoCancel(true);

        Intent notificationIntent = new Intent(getApplicationContext(), NotificationResultActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        manager.notify(2, notification);
    }

    //서비스가 종료될 때 호출되는 메서드
    @Override
    public void onDestroy() {
        running = false;
        super.onDestroy();
    }
}
