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
import com.example.cassianomoura.testeapp.control.RepositorioExercicios;

import static android.widget.Toast.LENGTH_LONG;

public class AAExerciciosActivity extends AppCompatActivity {

    private CriaBanco criaBanco;
    private SQLiteDatabase conexao;
    private static Context context;
    private ListView listViewExercicios;
    private ArrayAdapter<String> arrayAdapterExercicios;
    private RepositorioExercicios repositorioExercicios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aaexercicios);

        listViewExercicios = (ListView)findViewById(R.id.listViewExercicios);
        context = getApplicationContext();
        connectionTest();
        repositorioExercicios.inserirExerciciosTeste();
        arrayAdapterExercicios = repositorioExercicios.buscaExercicios(this);
        listViewExercicios.setAdapter(arrayAdapterExercicios);
    }
    public void connectionTest(){
        try{
            criaBanco = new CriaBanco(this);
            conexao = criaBanco.getWritableDatabase();
            Toast.makeText(context, "Conexão criada com sucesso!", LENGTH_LONG).show();
            repositorioExercicios = new RepositorioExercicios(conexao);
        }catch (SQLException e){
            Toast.makeText(context, e.getMessage(), LENGTH_LONG).show();
        }

    }
}
