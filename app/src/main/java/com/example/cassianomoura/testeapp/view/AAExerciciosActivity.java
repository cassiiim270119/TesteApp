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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cassianomoura.testeapp.R;
import com.example.cassianomoura.testeapp.control.AlarmReceiverExercicios;
import com.example.cassianomoura.testeapp.model.Banco;
import com.example.cassianomoura.testeapp.control.RepositorioExercicios;

import java.util.ArrayList;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class AAExerciciosActivity extends AppCompatActivity {

    private static String parametroAtual;
    private Banco banco;
    private SQLiteDatabase conexao;
    private Context context;
    private ListView listViewExercicio;
    private TextView tvExercicio;
    private Button btnLerExercicios;
    private Button btnSalvarExercicio;
    private ArrayAdapter<String> arrayAdapterExercicios;
    private RepositorioExercicios repositorioExercicios;
    private TextToSpeech tts;
    private PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aaexercicios);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                }
            }
        });

        listViewExercicio = (ListView)findViewById(R.id.listViewExercicios);
        context = getApplicationContext();
        connectionTest();
        arrayAdapterExercicios = repositorioExercicios.buscaExercicios(this);
        listViewExercicio.setAdapter(arrayAdapterExercicios);
        tvExercicio = (TextView)findViewById(R.id.txtTituloAAExercicios);
        btnLerExercicios = (Button)findViewById(R.id.btnLerExercicios);
        btnSalvarExercicio = (Button)findViewById(R.id.btnSalvarExercicio);
        tvExercicio.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getSpeechComando();
                return true;
            }
        });
        btnSalvarExercicio.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startGravarExercicio();
                return true;
            }
        });
        btnLerExercicios.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                readExercicios();
                return true;
            }
        });
        listViewExercicio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String exercicioSelecionado = adapterView.getItemAtPosition(i).toString().split(": ")[1];
                tts.speak("Remover o exercício "+exercicioSelecionado+".",TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        listViewExercicio.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String idARemover = adapterView.getItemAtPosition(i).toString().split(": ")[0];
                String exercicioRemovido = adapterView.getItemAtPosition(i).toString().split(": ")[1];
                if (repositorioExercicios.excluirExercicio(Integer.parseInt(idARemover)) > 0){
                    String concatenarId = "100" + String.valueOf(idARemover);
                    Intent intentAlarm = new Intent(AAExerciciosActivity.this, AlarmReceiverExercicios.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(concatenarId), intentAlarm, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                    tts.speak("Removido o exercício "+exercicioRemovido+".",TextToSpeech.QUEUE_FLUSH, null);
                    startAAExercicios();
                }
                return true;
            }
        });
    }
    public void connectionTest(){
        try{
            banco = new Banco(this);
            conexao = banco.getWritableDatabase();
            //Toast.makeText(context, "Conexão criada com sucesso!", LENGTH_LONG).show();
            repositorioExercicios = new RepositorioExercicios(conexao);
        }catch (SQLException e){
            Toast.makeText(context, e.getMessage(), LENGTH_LONG).show();
        }
    }

    public void speechHelpExercicios(View view){
        tts.speak("Tela de remédios. Pressione e segure para falar sua ação. Como, Excluir remédio 1, ou gravar remédio.", TextToSpeech.QUEUE_ADD, null);
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
    public void newExercicioSpeech(View view){
        tts.speak("Salvar novo exercício.", TextToSpeech.QUEUE_ADD, null);
    }

    public void readExerciciosSpeech(View view){
        tts.speak("Ler Exercícios.", TextToSpeech.QUEUE_FLUSH, null);
    }

    public void readExercicios(){
        for (int nAtv = 0; nAtv < arrayAdapterExercicios.getCount(); ++nAtv){
            tts.speak("Exercício "+ arrayAdapterExercicios.getItem(nAtv)+".", TextToSpeech.QUEUE_ADD, null);
        }
    }

    public void startAAExercicios(){
        Intent intent = new Intent(this, AAExerciciosActivity.class);
        startActivity(intent);
    }
    public void startGravarExercicio(){
        Intent intent = new Intent(this, GravarExercicioActivity.class);
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
                            startGravarExercicio();
                        }else if (acao.equals("excluir") || acao.equals("remover")){
                            repositorioExercicios.excluirExercicio(Integer.parseInt(resultado.get(0).split(" ")[2]));
                            tts.speak("exercício: "+resultado.get(0).split(" ")[2]+" removido com sucesso!", TextToSpeech.QUEUE_FLUSH, null);
                            startAAExercicios();
                        }else if (acao.equals("ler")){
                            //tts.speak("Eu entendi: "+resultado.get(0)+". Para confirmar, agite o aparelho.", TextToSpeech.QUEUE_FLUSH, null);
                            readExercicios();
                        }
                    }
                }
        }
    }

}
