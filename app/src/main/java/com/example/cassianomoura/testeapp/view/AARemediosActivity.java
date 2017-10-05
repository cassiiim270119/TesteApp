package com.example.cassianomoura.testeapp.view;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.cassianomoura.testeapp.R;
import com.example.cassianomoura.testeapp.model.CriaBanco;
import com.example.cassianomoura.testeapp.model.RepositorioRemedios;

import static android.widget.Toast.LENGTH_LONG;

public class AARemediosActivity extends AppCompatActivity {

    private CriaBanco criaBanco;
    private SQLiteDatabase conexao;
    private static Context context;
    private ListView listViewRemedios;
    private ArrayAdapter<String> arrayAdapterRemedios;
    private RepositorioRemedios repositorioRemedios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aaremedios);

        listViewRemedios = (ListView)findViewById(R.id.listViewRemdios);
        context = getApplicationContext();
        testarConexao();
        repositorioRemedios.inserirRemediosTeste();
        arrayAdapterRemedios = repositorioRemedios.buscaRemedios(this);
        listViewRemedios.setAdapter(arrayAdapterRemedios);
    }
    public void testarConexao(){
        try{
            criaBanco = new CriaBanco(this);
            conexao = criaBanco.getWritableDatabase();
            Toast.makeText(context, "Conexão criada com sucesso!", LENGTH_LONG).show();
        }catch (SQLException e){
            Toast.makeText(context, e.getMessage(), LENGTH_LONG).show();
        }

    }
}
