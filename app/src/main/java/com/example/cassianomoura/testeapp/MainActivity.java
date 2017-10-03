package com.example.cassianomoura.testeapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

import static android.widget.Toast.*;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private CriaBanco criaBanco;
    private SQLiteDatabase conexao;
    Context context = getApplicationContext();

    TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnEasyCall = (Button) findViewById(R.id.btn_EasyCall);
        Button btnIMC = (Button) findViewById(R.id.btn_IMC);
        Button btnDuhuMath = (Button) findViewById(R.id.btn_DuhuMath);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    Locale locale = new Locale("PT", "BR");
                    tts.setLanguage(locale);
                }
            }
        });

        btnEasyCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrate(1);
                tts.speak(getString(R.string.btnEasyCallSpeak), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnIMC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrate(2);
                tts.speak(getString(R.string.btnAgendaAcompanhamentoSpeak), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnDuhuMath.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrate(3);
                tts.speak(getString(R.string.btnJogosSpeak), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnIMC.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startAgendaDeAcompanhamento();
                return true;
            }
        });

        btnEasyCall.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startEasyCall();
                return true;
            }
        });
        testarConexao();
    }
    public void startEasyCall(){
        tts.speak(getString(R.string.easyCallStartSpeak), TextToSpeech.QUEUE_FLUSH, null);
        Intent intent = new Intent(this, EasyCallActivity.class);
        startActivity(intent);
    }

    public void startAgendaDeAcompanhamento(){
        tts.speak(getString(R.string.agendaAcompanhamentoStartSpeak), TextToSpeech.QUEUE_FLUSH, null);
        Intent intent = new Intent(this, AgendaAcompanhamentoActivity.class);
        startActivity(intent);
    }

    public void vibrate(int intensity){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            int vibrator = 100 * intensity;
                vibe.vibrate(vibrator);
        }
    }
    public void testarConexao(){
        try{
            criaBanco = new CriaBanco(this);
            conexao = criaBanco.getWritableDatabase();
            Toast.makeText(context, "Conxão criada com sucesso!", LENGTH_LONG).show();
        }catch (SQLException e){
            Toast.makeText(context, e.getMessage(), LENGTH_LONG).show();
        }

    }
}
