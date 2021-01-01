package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.R;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.receiver.ConnectionReceiver;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.SavingcoConstant;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.receiver.NotificationReceiver;

public class HomeActivity extends AppCompatActivity {
    private ConnectionReceiver receiver;
    private IntentFilter intentFilter;
    private static final int REQUEST_CODE_NOTIFICATION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_tips, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        receiver = new ConnectionReceiver();
        intentFilter = new IntentFilter(SavingcoConstant.BROADCAST_CONNECTION_INTENT_FILTER_NAME);

        issueNotification();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void issueNotification(){
        Intent notifyIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (getApplicationContext(), REQUEST_CODE_NOTIFICATION, notifyIntent, 0);

        AlarmManager alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 28);

        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}