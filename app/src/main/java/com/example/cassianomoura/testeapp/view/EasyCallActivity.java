package com.example.cassianomoura.testeapp.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cassianomoura.testeapp.R;

import java.util.Locale;

public class EasyCallActivity extends AppCompatActivity {
    TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_call);
        final EditText txtPhone = (EditText) findViewById(R.id.txtPhone);
        Button btnIndex00 = (Button) findViewById(R.id.btnIndex00);
        Button btnIndex01 = (Button) findViewById(R.id.btnIndex01);
        Button btnIndex02 = (Button) findViewById(R.id.btnIndex02);
        Button btnIndex03 = (Button) findViewById(R.id.btnIndex03);
        Button btnIndex10 = (Button) findViewById(R.id.btnIndex10);
        Button btnIndex20 = (Button) findViewById(R.id.btnIndex20);
        Button btnIndex30 = (Button) findViewById(R.id.btnIndex30);
        Button btnIndex40 = (Button) findViewById(R.id.btnIndex40);
        Button btnOne = (Button) findViewById(R.id.btnOne);
        Button btnTwo = (Button) findViewById(R.id.btnTwo);
        Button btnThree = (Button) findViewById(R.id.btnThree);
        Button btnFour = (Button) findViewById(R.id.btnFour);
        Button btnFive = (Button) findViewById(R.id.btnFive);
        Button btnSix = (Button) findViewById(R.id.btnSix);
        Button btnSeven = (Button) findViewById(R.id.btnSeven);
        Button btnEight = (Button) findViewById(R.id.btnEight);
        Button btnNine = (Button) findViewById(R.id.btnNine);
        Button btnAsterisk = (Button) findViewById(R.id.btnAsterisk);
        Button btnZero = (Button) findViewById(R.id.btnZero);
        Button btnSquare = (Button) findViewById(R.id.btnSquare);
        Button btnClear = (Button) findViewById(R.id.btnClear);
        Button btnCall = (Button) findViewById(R.id.btnCall);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    Locale locale = new Locale("PT", "BR");
                    tts.setLanguage(locale);
                }
            }
        });

        //Índices de Linha 0: colunas 1, 2 e 3
        btnIndex01.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrate(1);
            }
        });

        btnIndex02.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrate(2);
            }
        });

        btnIndex03.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrate(3);
            }
        });

        //Índices de Linhas 1, 2, 3 e 4: coluna 0
        btnIndex10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrate(1);
            }
        });

        btnIndex20.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrate(2);
            }
        });

        btnIndex30.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrate(3);
            }
        });

        btnIndex40.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrate(4);
            }
        });

        //Conteúdo numérico
        btnOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tts.speak(getString(R.string.speakOne), TextToSpeech.QUEUE_FLUSH, null);
                vibrate(1);
            }
        });

        btnOne.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                txtPhone.setText(txtPhone.getText() + "1");
                tts.speak(getString(R.string.speakOne), TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
        });

        btnTwo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tts.speak(getString(R.string.speakTwo), TextToSpeech.QUEUE_FLUSH, null);
                vibrate(1);
            }
        });

        btnTwo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                txtPhone.setText(txtPhone.getText() + "2");
                tts.speak(getString(R.string.speakTwo), TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
        });

        btnThree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tts.speak(getString(R.string.speakThree), TextToSpeech.QUEUE_FLUSH, null);
                vibrate(1);
            }
        });

        btnThree.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                txtPhone.setText(txtPhone.getText() + "3");
                tts.speak(getString(R.string.speakThree), TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
        });

        btnFour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tts.speak(getString(R.string.speakFour), TextToSpeech.QUEUE_FLUSH, null);
                vibrate(1);
            }
        });

        btnFour.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                txtPhone.setText(txtPhone.getText() + "4");
                tts.speak(getString(R.string.speakFour), TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
        });

        btnFive.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tts.speak(getString(R.string.speakFive), TextToSpeech.QUEUE_FLUSH, null);
                vibrate(1);
            }
        });

        btnFive.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                txtPhone.setText(txtPhone.getText() + "5");
                tts.speak(getString(R.string.speakFive), TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
        });

        btnSix.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tts.speak(getString(R.string.speakSix), TextToSpeech.QUEUE_FLUSH, null);
                vibrate(1);
            }
        });

        btnSix.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                txtPhone.setText(txtPhone.getText() + "6");
                tts.speak(getString(R.string.speakSix), TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
        });

        btnSeven.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tts.speak(getString(R.string.speakSeven), TextToSpeech.QUEUE_FLUSH, null);
                vibrate(1);
            }
        });

        btnSeven.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                txtPhone.setText(txtPhone.getText() + "7");
                tts.speak(getString(R.string.speakSeven), TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
        });

        btnEight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tts.speak(getString(R.string.speakEight), TextToSpeech.QUEUE_FLUSH, null);
                vibrate(1);
            }
        });

        btnEight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                txtPhone.setText(txtPhone.getText() + "8");
                tts.speak(getString(R.string.speakEight), TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
        });

        btnNine.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tts.speak(getString(R.string.speakNine), TextToSpeech.QUEUE_FLUSH, null);
                vibrate(1);
            }
        });

        btnNine.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                txtPhone.setText(txtPhone.getText() + "9");
                tts.speak(getString(R.string.speakNine), TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
        });

        btnAsterisk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tts.speak(getString(R.string.speakAsterisc), TextToSpeech.QUEUE_FLUSH, null);
                vibrate(1);
            }
        });

        btnAsterisk.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                txtPhone.setText(txtPhone.getText() + "*");
                tts.speak(getString(R.string.speakAsterisc), TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
        });

        btnZero.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tts.speak(getString(R.string.speakZero), TextToSpeech.QUEUE_FLUSH, null);
                vibrate(1);
            }
        });

        btnZero.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                txtPhone.setText(txtPhone.getText() + "0");
                tts.speak(getString(R.string.speakZero), TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
        });

        btnSquare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tts.speak(getString(R.string.speakSquare), TextToSpeech.QUEUE_FLUSH, null);
                vibrate(1);
            }
        });

        btnSquare.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                txtPhone.setText(txtPhone.getText() + "#");
                tts.speak(getString(R.string.speakSquare), TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
        });

        //Botão limpar
        btnClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tts.speak(getString(R.string.btnLimparOnClick), TextToSpeech.QUEUE_FLUSH, null);
                vibrate(1);
            }
        });

        btnClear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                txtPhone.setText("");
                tts.speak(getString(R.string.btnLimparOnLongClick), TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
        });

        //Botão chamar
        btnCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tts.speak(getString(R.string.btnFazerChamadaOnClick), TextToSpeech.QUEUE_FLUSH, null);
                vibrate(1);
            }
        });

        btnCall.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                phoneCall(String.valueOf(txtPhone.getText()));
                tts.speak(getString(R.string.btnFazerChamadaOnLongClick), TextToSpeech.QUEUE_FLUSH, null);
                return true;
            }
        });
    }

    public void vibrate(int intensity){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            int vibrator = 70 * intensity;
            vibe.vibrate(vibrator);
        }
    }
    public void phoneCall(String number) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }
}
