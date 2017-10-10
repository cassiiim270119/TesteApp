package com.example.cassianomoura.testeapp.view;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

import com.example.cassianomoura.testeapp.R;
import com.example.cassianomoura.testeapp.model.CriaBanco;
import com.example.cassianomoura.testeapp.control.RepositorioCompromissos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class AACompromissosActivity extends AppCompatActivity {

    private static String parametroAtual;
    private CriaBanco criaBanco;
    private SQLiteDatabase conexao;
    private static Context context;
    private ListView listViewCompromissos;
    private Button btnGravarCompromissoTitulo;
    private Button btnGravarCompromissoHorario;
    private Button btnGravarCompromissoConfirm;
    private ArrayAdapter<String> arrayAdapterCompromissos;
    private RepositorioCompromissos repositorioCompromissos;
    private TextToSpeech tts;
    private ArrayList<String> dadosParaBd = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aacompromissos);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                }
            }
        });
        listViewCompromissos = (ListView)findViewById(R.id.listViewCompromissos);
        context = getApplicationContext();
        connectionTest();
        //repositorioCompromissos.excluirCompromissoTeste(0);
        arrayAdapterCompromissos = repositorioCompromissos.buscaCompromissos(this);
        listViewCompromissos.setAdapter(arrayAdapterCompromissos);
        btnGravarCompromissoTitulo = (Button)findViewById(R.id.btnGravarCompromissoTitulo);
        btnGravarCompromissoHorario = (Button)findViewById(R.id.btnGravarCompromissoHorario);
        btnGravarCompromissoConfirm = (Button)findViewById(R.id.btnGravarCompromissoConfirm);
        btnGravarCompromissoTitulo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getSpeechTitulo();
                return true;
            }
        });
        btnGravarCompromissoHorario.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getSpeechHorario();
                return true;
            }
        });
        btnGravarCompromissoConfirm.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                confirmIncludeCompromisso();
                return true;
            }
        });
    }
    public void connectionTest(){
        try{
            criaBanco = new CriaBanco(this);
            conexao = criaBanco.getWritableDatabase();
            Toast.makeText(context, "Conexão criada com sucesso!", LENGTH_LONG).show();
            repositorioCompromissos = new RepositorioCompromissos(conexao);
        }catch (SQLException e){
            Toast.makeText(context, e.getMessage(), LENGTH_LONG).show();
        }
    }
    public void speechTitulo(View view) {
        tts.speak("Falar o compromisso.", TextToSpeech.QUEUE_FLUSH, null);
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
        tts.speak("Falar o horário do compromisso.", TextToSpeech.QUEUE_FLUSH, null);
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

    public void speechConfirmIncludeCompromisso(View view){
        tts.speak("Confirmar compromisso.", TextToSpeech.QUEUE_FLUSH, null);
    }
    public void confirmIncludeCompromisso(){
        if (repositorioCompromissos.insertCompromisso(dadosParaBd.get(0), dadosParaBd.get(1)) > 0){
            tts.speak("Compromisso: "+dadosParaBd.get(0)+" às "+dadosParaBd.get(1)+" armazenado com sucesso!", TextToSpeech.QUEUE_FLUSH, null);
        }
        startAACompromissos();
    }

    public void startAACompromissos(){
        Intent intent = new Intent(this, AACompromissosActivity.class);
        startActivity(intent);
    }
    public void readCompromissos(View view){
        for (int nComp = 0 ; nComp < arrayAdapterCompromissos.getCount();++nComp){
            tts.speak("Compromisso "+(nComp+1)+". "+arrayAdapterCompromissos.getItem(nComp)+".", TextToSpeech.QUEUE_ADD, null);
        }
    }

}
