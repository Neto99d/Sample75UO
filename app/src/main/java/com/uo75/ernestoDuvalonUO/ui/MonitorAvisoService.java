package com.uo75.ernestoDuvalonUO.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MonitorAvisoService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /*Intent i = new Intent(context, MainEventos.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);*/
        Intent service = new Intent(context.getApplicationContext(), ServicioSearchAvisos.class);
        context.startService(service);
    }
}