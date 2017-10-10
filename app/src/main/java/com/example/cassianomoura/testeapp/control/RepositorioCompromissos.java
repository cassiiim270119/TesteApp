package com.example.cassianomoura.testeapp.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Cassiano Moura on 05/10/2017.
 */

public class RepositorioCompromissos {
    private SQLiteDatabase conexao;

    public RepositorioCompromissos(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public void inserirCompromissosTeste(){
        ContentValues contentValues = new ContentValues();
        contentValues.put("titulo","Reunião escolar do júnior");
        contentValues.put("horario", "19:00");
        conexao.insertOrThrow("compromissos", null, contentValues);

    }

    public long insertCompromisso(String titulo, String horario){
        ContentValues contentValues = new ContentValues();
        contentValues.put("titulo",titulo);
        contentValues.put("horario", horario);
        return conexao.insertOrThrow("compromissos", null, contentValues);
    }

    public void excluirCompromissoTeste(int codigo){
        /*String[] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);
        conexao.delete("compromissos", "id = ?", parametros);*/
        conexao.delete("compromissos", null, null);
    }

    public ArrayAdapter<String> buscaCompromissos(Context context){
        ArrayAdapter<String> arrayAdapterCompromissos = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = conexao.rawQuery("SELECT * FROM compromissos", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
                String horario = cursor.getString(cursor.getColumnIndexOrThrow("horario"));
                arrayAdapterCompromissos.add(titulo+" - "+horario);
            }while (cursor.moveToNext());
        }
        return arrayAdapterCompromissos;
    }
}
