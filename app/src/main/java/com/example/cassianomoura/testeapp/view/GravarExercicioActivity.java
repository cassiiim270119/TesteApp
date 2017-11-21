package com.example.cassianomoura.testeapp.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cassianomoura.testeapp.R;
import com.example.cassianomoura.testeapp.control.AlarmReceiverExercicios;
import com.example.cassianomoura.testeapp.control.RepositorioExercicios;
import com.example.cassianomoura.testeapp.model.Banco;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class GravarExercicioActivity extends AppCompatActivity {

    private Banco banco;
    private SQLiteDatabase conexao;
    private static Context context;
    private Button btnGravarExercicioConfirm;
    private EditText txtTituloExercicio;
    private EditText txtHorarioExercicio;
    private TextToSpeech tts;
    private RepositorioExercicios repositorioExercicios;
    private static String parametroAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravar_exercicio);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                }
            }
        });
        context = getApplicationContext();
        testarConexao();

        txtTituloExercicio = (EditText) findViewById(R.id.txtTituloExercicio);
        txtHorarioExercicio = (EditText) findViewById(R.id.txtHorarioExercicio);
        btnGravarExercicioConfirm = (Button)findViewById(R.id.btnConfirmarExercicio);
        txtTituloExercicio.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getSpeechTitulo();
                return true;
            }
        });
        txtHorarioExercicio.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getSpeechHorario();
                return true;
            }
        });
        btnGravarExercicioConfirm.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Calendar agora = Calendar.getInstance();
                String diaHoje = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                String mesHoje = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
                String anoHoje = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                String horasAlarme = txtHorarioExercicio.getText().toString().split(":")[0];
                String minutosAlarme = txtHorarioExercicio.getText().toString().split(":")[1];
                String myDate = anoHoje + "/" + mesHoje + "/" + diaHoje + " " + horasAlarme + ":" + minutosAlarme + ":00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = null;
                try {
                    date = sdf.parse(myDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long millisAlarme = date.getTime();
                if (millisAlarme < agora.getTimeInMillis()){
                    if ((Integer.parseInt(mesHoje)==1)||(Integer.parseInt(mesHoje)==3)||(Integer.parseInt(mesHoje)==5)||
                            (Integer.parseInt(mesHoje)==7)||(Integer.parseInt(mesHoje)==8)||
                            (Integer.parseInt(mesHoje)==10)||(Integer.parseInt(mesHoje)==12)){
                        if (Integer.parseInt(diaHoje) < 31){
                            diaHoje = String.valueOf(Integer.parseInt(diaHoje)+1);
                        }else{
                            if (Integer.parseInt(mesHoje)<12){
                                mesHoje = String.valueOf(Integer.parseInt(mesHoje)+1);
                            }else{
                                anoHoje = String.valueOf(Integer.parseInt(anoHoje)+1);
                                mesHoje = "1";
                            }
                            diaHoje = "1";
                        }
                    }else if (Integer.parseInt(mesHoje)==2){
                        if (Integer.parseInt(diaHoje) < 28){
                            diaHoje = String.valueOf(Integer.parseInt(diaHoje)+1);
                        }else{
                            if (Integer.parseInt(mesHoje)<12){
                                mesHoje = String.valueOf(Integer.parseInt(mesHoje)+1);
                            }else{
                                anoHoje = String.valueOf(Integer.parseInt(anoHoje)+1);
                                mesHoje = "1";
                            }
                            diaHoje = "1";
                        }
                    }else{
                        if (Integer.parseInt(diaHoje) < 30){
                            diaHoje = String.valueOf(Integer.parseInt(diaHoje)+1);
                        }else{
                            if (Integer.parseInt(mesHoje)<12){
                                mesHoje = String.valueOf(Integer.parseInt(mesHoje)+1);
                            }else{
                                anoHoje = String.valueOf(Integer.parseInt(anoHoje)+1);
                                mesHoje = "1";
                            }
                            diaHoje = "1";
                        }
                    }
                    myDate = anoHoje + "/" + mesHoje + "/" + diaHoje + " " + horasAlarme + ":" + minutosAlarme + ":00";
                    sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    try {
                        date = sdf.parse(myDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    millisAlarme = date.getTime();
                }
                long idInserido = confirmIncludeExercicios();
                String concatenarId = "100" + String.valueOf(idInserido);
                long intervaloMillisecs = 120000;
                Intent intentAlarm = new Intent(GravarExercicioActivity.this, AlarmReceiverExercicios.class);
                intentAlarm.putExtra("idInserido", Integer.parseInt(concatenarId));
                intentAlarm.putExtra("tituloInserido", txtTituloExercicio.getText().toString());
                intentAlarm.putExtra("horarioInserido", txtHorarioExercicio.getText().toString());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(concatenarId), intentAlarm, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, millisAlarme, intervaloMillisecs, pendingIntent);
                return true;
            }
        });
    }
    public void testarConexao(){
        try{
            banco = new Banco(this);
            conexao = banco.getWritableDatabase();
            Toast.makeText(context, "Conexão criada com sucesso!", LENGTH_LONG).show();
            repositorioExercicios = new RepositorioExercicios(conexao);
        }catch (SQLException e){
            Toast.makeText(context, e.getMessage(), LENGTH_LONG).show();
        }
    }

    public void speechTitulo(View view) {
        tts.speak("Falar a atvidade.", TextToSpeech.QUEUE_ADD, null);
    }
    public void getSpeechTitulo(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
        parametroAtual = "titulo";
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 10);
        }else{
            tts.speak("Desculpe, seu aparelho não pode receber suas falas.", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void speechHorario(View view){
        tts.speak("Falar o horário do exercício.", TextToSpeech.QUEUE_ADD, null);
    }
    public void getSpeechHorario(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
        parametroAtual = "horario";
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 10);
        }else{
            tts.speak("Desculpe, seu aparelho não pode receber suas falas.", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void speechConfirmIncludeExercicios(View view){
        tts.speak("Armazenar exercício.", TextToSpeech.QUEUE_FLUSH, null);
    }
    public long confirmIncludeExercicios(){
        long idInserido = repositorioExercicios.insertExercicio(txtTituloExercicio.getText().toString(), txtHorarioExercicio.getText().toString());
        if (idInserido > 0){
            tts.speak("Exercício: " + txtTituloExercicio.getText().toString() + " às " +  txtHorarioExercicio.getText().toString() + " armazenado com o identificador: " + idInserido + ".", TextToSpeech.QUEUE_FLUSH, null);
        }
        startAAExercicios();
        return idInserido;
    }
    public void startAAExercicios(){
        Intent intent = new Intent(this, AAExerciciosActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if (resultCode == RESULT_OK && data != null){
                    ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (parametroAtual.equals("titulo")) {
                        tts.speak("Eu entendi: "+resultado.get(0)+".", TextToSpeech.QUEUE_FLUSH, null);
                        txtTituloExercicio.setText(resultado.get(0));
                    }else if (parametroAtual.equals("horario")){
                        tts.speak("Eu entendi: "+resultado.get(0)+".", TextToSpeech.QUEUE_FLUSH, null);
                        if (resultado.get(0).contains("horas")){
                            txtHorarioExercicio.setText(resultado.get(0).split(" horas")[0]+":00");
                        }else{
                            txtHorarioExercicio.setText(resultado.get(0));
                        }
                    }
                }
        }
    }
}
