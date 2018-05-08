package com.miaxis.escort.view.broadcast;

/**
 * Created by 一非 on 2018/5/8.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.miaxis.escort.view.activity.LoginActivity;

public class BootRestartReceiver extends BroadcastReceiver
{
    private final String ACTION = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION));
        {
            Intent intent2 = new Intent(context, LoginActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent2);
        }

    }
}
