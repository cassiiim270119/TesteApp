package com.example.cassianomoura.testeapp.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Cassiano Moura on 05/10/2017.
 */

public class RepositorioExercicios {
    private SQLiteDatabase conexao;

    public RepositorioExercicios(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public long insertExercicio(String titulo, String horario){
        ContentValues contentValues = new ContentValues();
        contentValues.put("titulo",titulo);
        contentValues.put("horario", horario);
        return conexao.insertOrThrow("exercicios", null, contentValues);
    }

    public int excluirExercicio(int id){
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        return conexao.delete("exercicios", "id = ?", parametros);
    }

    public ArrayAdapter<String> buscaExercicios(Context context){
        ArrayAdapter<String> arrayAdapterExercicios = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = conexao.rawQuery("SELECT * FROM exercicios", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
                String horario = cursor.getString(cursor.getColumnIndexOrThrow("horario"));
                String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                arrayAdapterExercicios.add(id + ": "+titulo+" - "+horario);
            }while (cursor.moveToNext());
        }
        return arrayAdapterExercicios;
    }
}
