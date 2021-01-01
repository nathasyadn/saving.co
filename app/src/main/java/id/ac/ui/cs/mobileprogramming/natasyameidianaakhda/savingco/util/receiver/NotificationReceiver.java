package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.service.NotificationService;

public class NotificationReceiver extends BroadcastReceiver {
    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, NotificationService.class);
        context.startService(i);
    }
}
