package com.example.cassianomoura.testeapp.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cassianomoura.testeapp.R;
import com.example.cassianomoura.testeapp.model.CriaBanco;

import java.util.ArrayList;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;


public class MainActivity extends AppCompatActivity {
    private Button btnEasyCall;
    private Button btnIMC;
    private Button btnDuhuMath;
    private TextToSpeech tts;
    private TextView txtTituloMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTituloMain = (TextView) findViewById(R.id.txtTituloMain);
        btnEasyCall = (Button) findViewById(R.id.btn_EasyCall);
        btnIMC = (Button) findViewById(R.id.btn_IMC);
        btnDuhuMath = (Button) findViewById(R.id.btn_DuhuMath);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                }
            }
        });

        txtTituloMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tts.speak("Plataforma Visual. Mantenha o dedo pressionado aqui para dizer se você deseja fazer chamada, abrir agenda de acompanhamento ou abrir a sessão de jogos.", TextToSpeech.QUEUE_FLUSH, null);
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

        txtTituloMain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getSpeechComando();
                return true;
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
    public void getSpeechComando(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 10);
        }else{
            tts.speak("Desculpe, seu aparelho não pode receber suas falas.", TextToSpeech.QUEUE_FLUSH, null);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if (resultCode == RESULT_OK && data != null){
                    ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (resultado.get(0).equalsIgnoreCase("fazer chamada")){
                        startEasyCall();
                    }else if (resultado.get(0).equalsIgnoreCase("abrir agenda de acompanhamento")){
                        startAgendaDeAcompanhamento();
                    }else if (resultado.get(0).equalsIgnoreCase("abrir sessão de jogos")){
                        tts.speak("Desculpe, função ainda indisponível!", TextToSpeech.QUEUE_FLUSH, null);
                    }else {
                        tts.speak("Desculpe, não reconheci o que você disse como uma função. Tente novamente.", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
        }
    }

}
