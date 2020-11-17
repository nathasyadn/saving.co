package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.R;

public class ConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SavingcoConstant.BROADCAST_CONNECTION_INTENT_FILTER_NAME)) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if (!isConnected) {
                Toast.makeText(context, R.string.toastNetwork, Toast.LENGTH_LONG).show();
            }
        }
    }
}