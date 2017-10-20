package com.example.cassianomoura.testeapp.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cassianomoura.testeapp.R;

import java.util.ArrayList;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class EasyCallActivity extends AppCompatActivity {
    private TextToSpeech tts;
    private Button btnIndex00;
    private Button btnIndex01;
    private Button btnIndex02;
    private Button btnIndex03;
    private Button btnIndex10;
    private Button btnIndex20;
    private Button btnIndex30;
    private Button btnIndex40;
    private Button btnOne;
    private Button btnTwo;
    private Button btnThree;
    private Button btnFour;
    private Button btnFive;
    private Button btnSix;
    private Button btnSeven;
    private Button btnEight;
    private Button btnNine;
    private Button btnAsterisk;
    private Button btnZero;
    private Button btnSquare;
    private Button btnClear;
    private Button btnCall;
    private TextView txtPhone;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_call);
        context = getApplicationContext();
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        btnIndex00 = (Button) findViewById(R.id.btnIndex00);
        btnIndex01 = (Button) findViewById(R.id.btnIndex01);
        btnIndex02 = (Button) findViewById(R.id.btnIndex02);
        btnIndex03 = (Button) findViewById(R.id.btnIndex03);
        btnIndex10 = (Button) findViewById(R.id.btnIndex10);
        btnIndex20 = (Button) findViewById(R.id.btnIndex20);
        btnIndex30 = (Button) findViewById(R.id.btnIndex30);
        btnIndex40 = (Button) findViewById(R.id.btnIndex40);
        btnOne = (Button) findViewById(R.id.btnOne);
        btnTwo = (Button) findViewById(R.id.btnTwo);
        btnThree = (Button) findViewById(R.id.btnThree);
        btnFour = (Button) findViewById(R.id.btnFour);
        btnFive = (Button) findViewById(R.id.btnFive);
        btnSix = (Button) findViewById(R.id.btnSix);
        btnSeven = (Button) findViewById(R.id.btnSeven);
        btnEight = (Button) findViewById(R.id.btnEight);
        btnNine = (Button) findViewById(R.id.btnNine);
        btnAsterisk = (Button) findViewById(R.id.btnAsterisk);
        btnZero = (Button) findViewById(R.id.btnZero);
        btnSquare = (Button) findViewById(R.id.btnSquare);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnCall = (Button) findViewById(R.id.btnCall);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                }
            }
        });

        txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("Fale o número a chamar.", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        txtPhone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getSpeechNumber();
                return true;
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
                phoneCall(txtPhone.getText().toString());
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
            Toast.makeText(context, "Por favor, verifique a permissão para o aplicativo realizar chamadas.", LENGTH_LONG).show();
            tts.speak("Por favor, verifique a permissão para o aplicativo realizar chamadas.", TextToSpeech.QUEUE_FLUSH, null);
            return;
        }else{
            tts.speak(getString(R.string.btnFazerChamadaOnLongClick), TextToSpeech.QUEUE_FLUSH, null);
        }
        startActivity(callIntent);
    }
    public void getSpeechNumber(){
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
                    String resultadoString = preparaString(resultado.get(0));
                    String[] numbers = resultadoString.split(" ");
                    String finalNumber = "";
                    for (int i=0; i<numbers.length; i++){
                        if (numbers[i].equalsIgnoreCase("zero") || numbers[i].equalsIgnoreCase("0")){
                            finalNumber += '0';
                        }else if (numbers[i].equalsIgnoreCase("um") || numbers[i].equalsIgnoreCase("1")){
                            finalNumber += '1';
                        }else if (numbers[i].equalsIgnoreCase("dois") || numbers[i].equalsIgnoreCase("2")){
                            finalNumber += '2';
                        }else if (numbers[i].equalsIgnoreCase("três") || numbers[i].equalsIgnoreCase("3")){
                            finalNumber += '3';
                        }else if (numbers[i].equalsIgnoreCase("quatro") || numbers[i].equalsIgnoreCase("4")){
                            finalNumber += '4';
                        }else if (numbers[i].equalsIgnoreCase("cinco") || numbers[i].equalsIgnoreCase("5")){
                            finalNumber += '5';
                        }else if (numbers[i].equalsIgnoreCase("seis") || numbers[i].equalsIgnoreCase("meia") || numbers[i].equalsIgnoreCase("6")){
                            finalNumber += '6';
                        }else if (numbers[i].equalsIgnoreCase("sete") || numbers[i].equalsIgnoreCase("7")){
                            finalNumber += '7';
                        }else if (numbers[i].equalsIgnoreCase("oito") || numbers[i].equalsIgnoreCase("8")){
                            finalNumber += '8';
                        }else if (numbers[i].equalsIgnoreCase("nove") || numbers[i].equalsIgnoreCase("9")){
                            finalNumber += '9';
                        }else if (numbers[i].equalsIgnoreCase("asterisco") || numbers[i].equalsIgnoreCase("*")){
                            finalNumber += '*';
                        }else if (numbers[i].equalsIgnoreCase("quadrado") || numbers[i].equalsIgnoreCase("cerquilha") || numbers[i].equalsIgnoreCase("#")){
                            finalNumber += '#';
                        }
                    }
                    txtPhone.setText(finalNumber);
                    String stringParaFala = preparaString(finalNumber);
                    tts.speak("Digitado: " + stringParaFala + ".", TextToSpeech.QUEUE_FLUSH, null);
                }
        }
    }
    private String preparaString(String resultadoString){
        if (resultadoString.contains("0")){
            resultadoString = resultadoString.replace("0", " 0 ");
        }
        if (resultadoString.contains("1")){
            resultadoString = resultadoString.replace("1", " 1 ");
        }
        if (resultadoString.contains("2")){
            resultadoString = resultadoString.replace("2", " 2 ");
        }
        if (resultadoString.contains("3")){
            resultadoString = resultadoString.replace("3", " 3 ");
        }
        if (resultadoString.contains("4")){
            resultadoString = resultadoString.replace("4", " 4 ");
        }
        if (resultadoString.contains("5")){
            resultadoString = resultadoString.replace("5", " 5 ");
        }
        if (resultadoString.contains("6")){
            resultadoString = resultadoString.replace("6", " 6 ");
        }
        if (resultadoString.contains("7")){
            resultadoString = resultadoString.replace("7", " 7 ");
        }
        if (resultadoString.contains("8")){
            resultadoString = resultadoString.replace("8", " 8 ");
        }
        if (resultadoString.contains("9")){
            resultadoString = resultadoString.replace("9", " 9 ");
        }
        return resultadoString;
    }
}
