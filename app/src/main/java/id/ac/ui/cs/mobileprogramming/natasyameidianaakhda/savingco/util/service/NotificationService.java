package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.R;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.home.HomeActivity;

public class NotificationService extends IntentService {
    private static final int NOTIFICATION_ID = 3;
    private static final int REQUEST_CODE_NOTIFICATION = 2;

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationCompat.Builder builder = createNotification();

        Intent notifyIntent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE_NOTIFICATION,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);

        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }


    private NotificationCompat.Builder createNotification() {
        Context context = getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            makeNotificationChannel(getString(R.string.channel1),
                    getString(R.string.chanReminder), NotificationManager.IMPORTANCE_DEFAULT);
        }

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(this, getString(R.string.channel1));

        notification
                .setSmallIcon(R.mipmap.logo_foreground)
                .setContentTitle(getString(R.string.remind))
                .setContentText(getString(R.string.record))
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
