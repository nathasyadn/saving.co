package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.R;

public class ReminderService extends Service {
    private Handler handler = new Handler();
    private static final int NOTIFICATION_ID = 4;

    @Override
    public void onCreate() {
        super.onCreate();
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 5000);

    }

    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            showNotification();
            handler.postDelayed(this, 1000 * 60 * 10); // 10 minutes
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(sendUpdatesToUI);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification(){
        NotificationCompat.Builder builder = createNotification();

        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }
    private NotificationCompat.Builder createNotification() {
        Context context = getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            makeNotificationChannel("CHANNEL_2",
                    getString(R.string.chanReminder), NotificationManager.IMPORTANCE_DEFAULT);
        }

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(this,
                        "CHANNEL_2");

        notification
                .setSmallIcon(R.mipmap.logo_foreground)
                .setContentTitle(getString(R.string.remind))
                .setContentText(getString(R.string.reminderTrx))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.logo_foreground));

        NotificationManager notificationManager =
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        assert notificationManager != null;
        return notification;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void makeNotificationChannel(String id, String name, int importance)
    {
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setShowBadge(true);

        NotificationManager notificationManager =
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }
}