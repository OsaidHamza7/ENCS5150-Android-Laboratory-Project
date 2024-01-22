package com.example.andriodlabproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    Timer timer;
    Timer timer2;
    TimerTask timerTask;
    TimerTask timerTask2;
    String TAG = "Timers";
    int Your_X_SECS = 5;
    private static final int NOTIFICATION_ID = 123;
    private static final String NOTIFICATION_COME_BACK_TITLE = "Come Back Soon!";
    private static final String NOTIFICATION_COME_BACK_BODY = "Your dream car might be just a tap away.We regularly update our listings with exciting new vehicles.";
    private static final String NOTIFICATION_SPECIAL_OFFERS_TITLE = "Exclusive Special Offer Just for You!";
    private static final String NOTIFICATION_SPECIAL_OFFERS_BODY = "Exclusive discounts on select cars for a limited time! Tap to explore and save now!";
    private static final String MY_CHANNEL_ID = "my_chanel_1";
    private static final String MY_CHANNEL_NAME = "My channel";

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        startTimer();

        return START_STICKY;
    }


    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");


    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        stoptimertask();
        super.onDestroy();


    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();


    public void startTimer() {
        //set a new Timer
        timer = new Timer();
        timer2 = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();

        //run timer task once in 10 seconds
        timer.schedule(timerTask, 5000);
        timer2.schedule(timerTask2, 20000);
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        createNotificationBack(NOTIFICATION_COME_BACK_TITLE, NOTIFICATION_COME_BACK_BODY);
                    }
                });
            }
        };

        timerTask2 = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        createNotificationSpecialOffers(NOTIFICATION_SPECIAL_OFFERS_TITLE, NOTIFICATION_SPECIAL_OFFERS_BODY);
                    }
                });
            }
        };

    }
    public void createNotificationSpecialOffers(String title, String body) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_IMMUTABLE);
        createNotificationChannel();
        Bitmap bigPictureBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_sale);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                MY_CHANNEL_ID)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.baseline_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bigPictureBitmap)
                        .bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColorized(true)
                .setColor(Color.RED)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
    public void createNotificationBack(String title, String body) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_IMMUTABLE);
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                MY_CHANNEL_ID)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.baseline_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColorized(true)
                .setColor(Color.RED)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
    private void createNotificationChannel() {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(MY_CHANNEL_ID,
                MY_CHANNEL_NAME, importance);
        NotificationManager notificationManager =
                getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

}