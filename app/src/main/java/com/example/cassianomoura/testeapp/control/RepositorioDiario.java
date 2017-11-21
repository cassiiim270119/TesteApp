package com.example.cassianomoura.testeapp.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Cassiano Moura on 05/10/2017.
 */

public class RepositorioDiario {
    private SQLiteDatabase conexao;

    public RepositorioDiario(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public long insertAtividade(String titulo, String horario){
        ContentValues contentValues = new ContentValues();
        contentValues.put("titulo",titulo);
        contentValues.put("horario", horario);
        return conexao.insertOrThrow("atividades", null, contentValues);
    }

    public int excluirAtividade(int id){
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        return conexao.delete("atividades", "id = ?", parametros);
    }

    public ArrayAdapter<String> buscaDiario(Context context){
        ArrayAdapter<String> arrayAdapterAtividades = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = conexao.rawQuery("SELECT * FROM atividades", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
                String horario = cursor.getString(cursor.getColumnIndexOrThrow("horario"));
                String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                arrayAdapterAtividades.add(id + ": "+titulo+" - "+horario);
            }while (cursor.moveToNext());
        }
        return arrayAdapterAtividades;
    }
}
