package com.example.cassianomoura.testeapp.control;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;

import com.example.cassianomoura.testeapp.R;
import com.example.cassianomoura.testeapp.view.AgendaAcompanhamentoActivity;
import com.example.cassianomoura.testeapp.view.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Intent.getIntent;


/**
 * Created by Cassiano Moura on 13/11/2017.
 */

public class AlarmReceiverExercicios extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra("idInserido", 1);
        String titulo = intent.getStringExtra("tituloInserido");
        String horario = intent.getStringExtra("horarioInserido");

        gerarNotificacao(context, (int) id, titulo, horario);
    }
    public void gerarNotificacao(Context context, int id, String titulo, String horario){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, AgendaAcompanhamentoActivity.class), 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setTicker(titulo);
        notificationBuilder.setContentTitle("Exercício agora");
        notificationBuilder.setContentText(titulo + " marcado para " + horario);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setAutoCancel(true);

        Notification notification = notificationBuilder.build();
        notification.vibrate = new long[]{500, 1000, 500, 1000, 500};
        notificationManager.notify(id, notification);
        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();
        }catch (Exception e){}
    }


}
