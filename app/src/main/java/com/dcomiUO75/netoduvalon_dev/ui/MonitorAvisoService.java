package com.dcomiUO75.netoduvalon_dev.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MonitorAvisoService extends BroadcastReceiver {
    // Clase para iniciar servicio una vez iniciado el dispositivo
    // No funciona en algunas versiones recientes de Android a no ser que active el inicio automatico de la app
    @Override
    public void onReceive(Context context, Intent intent) {
        /*Intent i = new Intent(context, MainEventos.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);*/
        Intent service = new Intent(context.getApplicationContext(), ServicioSearchAvisos.class);
        context.startService(service);
    }
}