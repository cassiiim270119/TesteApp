package com.example.cassianomoura.testeapp.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Cassiano Moura on 03/10/2017.
 */

public class RepositorioRemedios {

    private SQLiteDatabase conexao;

    public RepositorioRemedios(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public void inserirRemediosTeste(){
        ContentValues contentValues = new ContentValues();
        contentValues.put("titulo","Cimelide");
        contentValues.put("horario", "22:45");
        conexao.insertOrThrow("remedios", null, contentValues);

    }

    public ArrayAdapter<String> buscaRemedios(Context context){
        ArrayAdapter<String> arrayAdapterRemedios = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = conexao.rawQuery("SELECT * FROM remedios", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
                String horario = cursor.getString(cursor.getColumnIndexOrThrow("horario"));
                arrayAdapterRemedios.add(titulo+" - "+horario);
            }while (cursor.moveToNext());
        }
        return arrayAdapterRemedios;
    }
}
