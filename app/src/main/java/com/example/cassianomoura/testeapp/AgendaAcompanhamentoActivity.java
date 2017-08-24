package com.example.cassianomoura.testeapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class AgendaAcompanhamentoActivity extends AppCompatActivity {

    TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_acompanhamento);

        Button btnAARemedios = (Button) findViewById(R.id.btnAARemedios);
        Button btnAACompromissos = (Button) findViewById(R.id.btnAACompromissos);
        Button btnAAExercicios = (Button) findViewById(R.id.btnAAExercicios);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    Locale locale = new Locale("PT", "BR");
                    tts.setLanguage(locale);
                }
            }
        });

        btnAARemedios.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrate(1);
                tts.speak(getString(R.string.btnCallAARemedios), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnAACompromissos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrate(2);
                tts.speak(getString(R.string.btnCallAACompromissos), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnAAExercicios.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrate(3);
                tts.speak(getString(R.string.btnCallAAExercicios), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnAACompromissos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //startAACompromissos();
                return true;
            }
        });

        btnAARemedios.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //startAARemedios();
                return true;
            }
        });

        btnAAExercicios.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //startAAExercicios();
                return true;
            }
        });
    }
    /*public void startEasyCall(){
        tts.speak("Digite o número para realizar sua chamada.", TextToSpeech.QUEUE_FLUSH, null);
        Intent intent = new Intent(this, EasyCallActivity.class);
        startActivity(intent);
    }*/

    public void vibrate(int intensity){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            int vibrator = 100 * intensity;
            vibe.vibrate(vibrator);
        }
    }
}
