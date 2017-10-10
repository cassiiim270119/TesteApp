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

    public void inserirExerciciosTeste(){
        ContentValues contentValues = new ContentValues();
        contentValues.put("titulo","Caminhada");
        contentValues.put("horario", "19:00");
        conexao.insertOrThrow("exercicios", null, contentValues);

    }

    public ArrayAdapter<String> buscaExercicios(Context context){
        ArrayAdapter<String> arrayAdapterExercicios = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = conexao.rawQuery("SELECT * FROM exercicios", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
                String horario = cursor.getString(cursor.getColumnIndexOrThrow("horario"));
                arrayAdapterExercicios.add(titulo+" - "+horario);
            }while (cursor.moveToNext());
        }
        return arrayAdapterExercicios;
    }
}
