package com.example.cassianomoura.testeapp.view;

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

import com.example.cassianomoura.testeapp.R;

import java.util.Locale;

public class AgendaAcompanhamentoActivity extends AppCompatActivity {
    private Button btnAARemedios;
    private Button btnAACompromissos;
    private Button btnAAExercicios;
    private TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_acompanhamento);

        btnAARemedios = (Button) findViewById(R.id.btnAARemedios);
        //btnAACompromissos = (Button) findViewById(R.id.btnAADiario);
        btnAAExercicios = (Button) findViewById(R.id.btnAAExercicios);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                }
            }
        });

        btnAARemedios.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrate(1);
                tts.speak(getString(R.string.btnCallAARemedios), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        /*btnAACompromissos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrate(2);
                tts.speak(getString(R.string.btnCallAADiario), TextToSpeech.QUEUE_FLUSH, null);
            }
        });*/

        btnAAExercicios.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrate(3);
                tts.speak(getString(R.string.btnCallAAExercicios), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        /*btnAACompromissos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startAADiario();
                return true;
            }
        });*/

        btnAARemedios.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startAARemedios();
                return true;
            }
        });

        btnAAExercicios.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startAAExercicios();
                return true;
            }
        });
    }
    public void startAARemedios(){
        tts.speak(getString(R.string.aaremediosStartSpeak), TextToSpeech.QUEUE_FLUSH, null);
        Intent intent = new Intent(this, AARemediosActivity.class);
        startActivity(intent);
    }

    public void startAADiario(){
        tts.speak(getString(R.string.aadiarioStartSpeak), TextToSpeech.QUEUE_FLUSH, null);
        Intent intent = new Intent(this, AADiarioActivity.class);
        startActivity(intent);
    }

    public void startAAExercicios(){
        tts.speak(getString(R.string.aaexerciciosStartSpeak), TextToSpeech.QUEUE_FLUSH, null);
        Intent intent = new Intent(this, AAExerciciosActivity.class);
        startActivity(intent);
    }

    public void vibrate(int intensity){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            int vibrator = 100 * intensity;
            vibe.vibrate(vibrator);
        }
    }
}
