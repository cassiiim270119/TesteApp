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
import com.example.cassianomoura.testeapp.control.RepositorioRemedios;
import com.example.cassianomoura.testeapp.model.Banco;

import java.util.ArrayList;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class AARemediosActivity extends AppCompatActivity {

    private static String parametroAtual;
    private Banco banco;
    private SQLiteDatabase conexao;
    private Context context;
    private ListView listViewRemedio;
    private TextView tvRemedio;
    private Button btnLerRemedios;
    private Button btnSalvarRemedio;
    private ArrayAdapter<String> arrayAdapterRemedios;
    private RepositorioRemedios repositorioRemedios;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aaremedios);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                }
            }
        });
        listViewRemedio = (ListView)findViewById(R.id.listViewRemedios);
        context = getApplicationContext();
        connectionTest();
        arrayAdapterRemedios = repositorioRemedios.buscaRemedios(this);
        listViewRemedio.setAdapter(arrayAdapterRemedios);
        tvRemedio = (TextView)findViewById(R.id.txtTituloAARemedios);
        btnLerRemedios = (Button)findViewById(R.id.btnLerRemedios);
        btnSalvarRemedio = (Button)findViewById(R.id.btnSalvarRemedio);
        tvRemedio.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getSpeechComando();
                return true;
            }
        });
        btnSalvarRemedio.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startGravarRemedio();
                return true;
            }
        });
        btnLerRemedios.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                readRemedios();
                return true;
            }
        });
        listViewRemedio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String remedioSelecionad0 = adapterView.getItemAtPosition(i).toString().split(": ")[1];
                tts.speak("Remover o remédio "+remedioSelecionad0+".",TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        listViewRemedio.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String idARemover = adapterView.getItemAtPosition(i).toString().split(": ")[0];
                String remedioRemovido = adapterView.getItemAtPosition(i).toString().split(": ")[1];
                if (repositorioRemedios.excluirRemedio(Integer.parseInt(idARemover)) > 0){
                    String concatenarId = "100" + String.valueOf(idARemover);
                    Intent intentAlarm = new Intent(AARemediosActivity.this, AlarmReceiverExercicios.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(concatenarId), intentAlarm, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                    tts.speak("Removido o remédio "+remedioRemovido+".",TextToSpeech.QUEUE_FLUSH, null);
                    startAARemedios();
                }
                return true;
            }
        });
    }
    public void connectionTest(){
        try{
            banco = new Banco(this);
            conexao = banco.getWritableDatabase();
            Toast.makeText(context, "Conexão criada com sucesso!", LENGTH_LONG).show();
            repositorioRemedios = new RepositorioRemedios(conexao);
        }catch (SQLException e){
            Toast.makeText(context, e.getMessage(), LENGTH_LONG).show();
        }
    }

    public void speechHelpRemedios(View view){
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
    public void newRemedioSpeech(View view){
        tts.speak("Salvar novo remédio.", TextToSpeech.QUEUE_ADD, null);
    }

    public void readRemediosSpeech(View view){
        tts.speak("Ler Remédios.", TextToSpeech.QUEUE_FLUSH, null);
    }

    public void readRemedios(){
        for (int nAtv = 0; nAtv < arrayAdapterRemedios.getCount(); ++nAtv){
            tts.speak("Remédio "+ arrayAdapterRemedios.getItem(nAtv)+".", TextToSpeech.QUEUE_ADD, null);
        }
    }

    public void startAARemedios(){
        Intent intent = new Intent(this, AARemediosActivity.class);
        startActivity(intent);
    }
    public void startGravarRemedio(){
        Intent intent = new Intent(this, GravarRemedioActivity.class);
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
                            startGravarRemedio();
                        }else if (acao.equals("excluir") || acao.equals("remover")){
                            repositorioRemedios.excluirRemedio(Integer.parseInt(resultado.get(0).split(" ")[2]));
                            tts.speak("remédio: "+resultado.get(0).split(" ")[2]+" removido com sucesso!", TextToSpeech.QUEUE_FLUSH, null);
                            startAARemedios();
                        }else if (acao.equals("ler")){
                            //tts.speak("Eu entendi: "+resultado.get(0)+". Para confirmar, agite o aparelho.", TextToSpeech.QUEUE_FLUSH, null);
                            readRemedios();
                        }
                    }
                }
        }
    }
}
