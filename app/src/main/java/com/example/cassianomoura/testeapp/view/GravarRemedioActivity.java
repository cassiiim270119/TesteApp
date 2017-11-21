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
import android.widget.EditText;
import android.widget.Toast;

import com.example.cassianomoura.testeapp.R;
import com.example.cassianomoura.testeapp.control.RepositorioRemedios;
import com.example.cassianomoura.testeapp.model.Banco;

import java.util.ArrayList;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class GravarRemedioActivity extends AppCompatActivity {
    private Banco banco;
    private SQLiteDatabase conexao;
    private static Context context;
    private Button btnConfirmarRemedio;
    private EditText txtTituloRemedio;
    private EditText txtHorarioRemedio;
    private TextToSpeech tts;
    private RepositorioRemedios repositorioRemedios;
    private static String parametroAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravar_remedio);
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
        txtTituloRemedio = (EditText) findViewById(R.id.txtTituloRemedio);
        txtHorarioRemedio = (EditText) findViewById(R.id.txtHorarioRemedio);
        btnConfirmarRemedio = (Button)findViewById(R.id.btnConfirmarRemedio);
        txtTituloRemedio.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getSpeechTitulo();
                return true;
            }
        });
        txtHorarioRemedio.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getSpeechHorario();
                return true;
            }
        });
        btnConfirmarRemedio.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                confirmIncludeRemedio();
                return true;
            }
        });
    }
    public void testarConexao(){
        try{
            banco = new Banco(this);
            conexao = banco.getWritableDatabase();
            Toast.makeText(context, "Conexão criada com sucesso!", LENGTH_LONG).show();
            repositorioRemedios = new RepositorioRemedios(conexao);
        }catch (SQLException e){
            Toast.makeText(context, e.getMessage(), LENGTH_LONG).show();
        }
    }

    public void speechTitulo(View view) {
        tts.speak("Falar o remédio.", TextToSpeech.QUEUE_ADD, null);
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
        tts.speak("Falar o horário do remédio.", TextToSpeech.QUEUE_ADD, null);
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

    public void speechConfirmIncludeRemedios(View view){
        tts.speak("Armazenar remédio.", TextToSpeech.QUEUE_FLUSH, null);
    }
    public void confirmIncludeRemedio(){
        long idInserido = repositorioRemedios.insertRemedio(txtTituloRemedio.getText().toString(), txtHorarioRemedio.getText().toString());
        if (idInserido > 0){
            tts.speak("Remédio: " + txtTituloRemedio.getText().toString() + " às " +  txtHorarioRemedio.getText().toString() + " armazenado com o identificador: " + idInserido + ".", TextToSpeech.QUEUE_FLUSH, null);
        }
        startAARemedios();
    }
    public void startAARemedios(){
        Intent intent = new Intent(this, AARemediosActivity.class);
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
                        txtTituloRemedio.setText(resultado.get(0));
                    }else if (parametroAtual.equals("horario")){
                        tts.speak("Eu entendi: "+resultado.get(0)+".", TextToSpeech.QUEUE_FLUSH, null);
                        if (resultado.get(0).contains("horas")){
                            txtHorarioRemedio.setText(resultado.get(0).split(" horas")[0]+":00");
                        }else{
                            txtHorarioRemedio.setText(resultado.get(0));
                        }
                    }
                }
        }
    }
}
