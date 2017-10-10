package com.example.cassianomoura.testeapp.view;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.example.cassianomoura.testeapp.R;
import com.example.cassianomoura.testeapp.control.RepositorioDiario;
import com.example.cassianomoura.testeapp.model.CriaBanco;

import java.util.ArrayList;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class AADiarioActivity extends AppCompatActivity {

    private static String parametroAtual;
    private CriaBanco criaBanco;
    private SQLiteDatabase conexao;
    private static Context context;
    private ListView listViewAtividade;
    private TextView tvAtividade;
    private Button btnLerDiario;
    private ArrayAdapter<String> arrayAdapterAtividade;
    private RepositorioDiario repositorioDiario;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadiario);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                }
            }
        });
        listViewAtividade = (ListView)findViewById(R.id.listViewDiario);
        context = getApplicationContext();
        connectionTest();
        arrayAdapterAtividade = repositorioDiario.buscaDiario(this);
        listViewAtividade.setAdapter(arrayAdapterAtividade);
        tvAtividade = (TextView)findViewById(R.id.txtTituloAADiario);
        btnLerDiario = (Button)findViewById(R.id.btnLerDiario);
        tvAtividade.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getSpeechComando();
                return true;
            }
        });

    }
    public void connectionTest(){
        try{
            criaBanco = new CriaBanco(this);
            conexao = criaBanco.getWritableDatabase();
            Toast.makeText(context, "Conexão criada com sucesso!", LENGTH_LONG).show();
            repositorioDiario = new RepositorioDiario(conexao);
        }catch (SQLException e){
            Toast.makeText(context, e.getMessage(), LENGTH_LONG).show();
        }
    }

    public void speechHelpDiario(View view){
        tts.speak("Tela de atividades. Pressione e segure para falar sua ação. Como, Excluir atividade 1, ou gravar atividade.", TextToSpeech.QUEUE_ADD, null);
    }
    public void getSpeechComando(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
        parametroAtual = "comando";
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 10);
        }else{
            tts.speak("Desculpe, seu aparelho não pode receber suas falas.", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void readDiario(View view){
        for (int nAtv = 0; nAtv < arrayAdapterAtividade.getCount(); ++nAtv){
            tts.speak("Atividade "+ arrayAdapterAtividade.getItem(nAtv)+".", TextToSpeech.QUEUE_ADD, null);
        }
    }

    public void startAADiario(){
        Intent intent = new Intent(this, AADiarioActivity.class);
        startActivity(intent);
    }
    public void startGravarAtividade(){
        Intent intent = new Intent(this, GravarAtividadeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if (resultCode == RESULT_OK && data != null){
                    ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (parametroAtual.equals("comando")){
                        String acao = resultado.get(0).split(" ")[0];
                        if (acao.equals("gravar") || acao.equals("salvar")){
                            //tts.speak("Eu entendi: "+resultado.get(0)+". Para confirmar, agite o aparelho.", TextToSpeech.QUEUE_FLUSH, null);
                            startGravarAtividade();
                        }else if (acao.equals("excluir") || acao.equals("remover")){
                            repositorioDiario.excluirAtividade(Integer.parseInt(resultado.get(0).split(" ")[2]));
                            tts.speak("Atividade: "+resultado.get(0).split(" ")[2]+" removido com sucesso!", TextToSpeech.QUEUE_FLUSH, null);
                            startAADiario();
                        }
                    }
                }
        }
    }
}
