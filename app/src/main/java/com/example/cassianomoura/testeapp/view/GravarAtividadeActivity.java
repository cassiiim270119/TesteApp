package com.example.cassianomoura.testeapp.view;

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
import android.widget.Toast;

import com.example.cassianomoura.testeapp.R;
import com.example.cassianomoura.testeapp.control.RepositorioDiario;
import com.example.cassianomoura.testeapp.model.CriaBanco;

import java.util.ArrayList;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class GravarAtividadeActivity extends AppCompatActivity {

    private CriaBanco criaBanco;
    private SQLiteDatabase conexao;
    private static Context context;
    private Button btnGravarAtividadeConfirm;
    private Button btnGravarAtividadeHorario;
    private Button btnGravarAtividadeTitulo;
    private ArrayList<String> dadosParaBd = new ArrayList<>();
    private TextToSpeech tts;
    private RepositorioDiario repositorioDiario;
    private static String parametroAtual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravar_atividade);
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
        btnGravarAtividadeTitulo = (Button)findViewById(R.id.btnGravarAtividadeTitulo);
        btnGravarAtividadeHorario = (Button)findViewById(R.id.btnGravarAtividadeHorario);
        btnGravarAtividadeConfirm = (Button)findViewById(R.id.btnConfirmarAtividade);
        btnGravarAtividadeTitulo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getSpeechTitulo();
                return true;
            }
        });
        btnGravarAtividadeHorario.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getSpeechHorario();
                return true;
            }
        });
        btnGravarAtividadeConfirm.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                confirmIncludeDiario();
                return true;
            }
        });
    }
    public void testarConexao(){
        try{
            criaBanco = new CriaBanco(this);
            conexao = criaBanco.getWritableDatabase();
            Toast.makeText(context, "Conexão criada com sucesso!", LENGTH_LONG).show();
            repositorioDiario = new RepositorioDiario(conexao);
        }catch (SQLException e){
            Toast.makeText(context, e.getMessage(), LENGTH_LONG).show();
        }
    }

    public void speechTitulo(View view) {
        tts.speak("Falar a atvidade.", TextToSpeech.QUEUE_FLUSH, null);
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
        tts.speak("Falar o horário da atividade.", TextToSpeech.QUEUE_FLUSH, null);
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

    public void speechConfirmIncludeDiario(View view){
        tts.speak("Armazenar atividade.", TextToSpeech.QUEUE_FLUSH, null);
    }
    public void confirmIncludeDiario(){
        if (repositorioDiario.insertAtividade(dadosParaBd.get(0), dadosParaBd.get(1)) > 0){
            tts.speak("Atividade: "+dadosParaBd.get(0)+" às "+dadosParaBd.get(1)+" armazenada com sucesso!", TextToSpeech.QUEUE_FLUSH, null);
        }
        startAADiario();
    }
    public void startAADiario(){
        Intent intent = new Intent(this, AADiarioActivity.class);
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
                        dadosParaBd.add(0, resultado.get(0));
                    }else if (parametroAtual.equals("horario")){
                        tts.speak("Eu entendi: "+resultado.get(0)+".", TextToSpeech.QUEUE_FLUSH, null);
                        if (resultado.get(0).contains("horas")){
                            dadosParaBd.add(1, resultado.get(0).split(" horas")[0]+":00");
                        }else{
                            dadosParaBd.add(1, resultado.get(0));
                        }
                    }
                }
        }
    }
}
