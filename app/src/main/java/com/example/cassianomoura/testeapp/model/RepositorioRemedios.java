package com.example.cassianomoura.testeapp.model;

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
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("titulo","Cimelide");
        //contentValues1.put("horario", "22:45");
        conexao.insertOrThrow("remedios", null, contentValues1);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("titulo","Ibuoprofeno");
        //contentValues2.put("horario", "22:45");
        conexao.insertOrThrow("remedios", null, contentValues2);

        ContentValues contentValues3 = new ContentValues();
        contentValues3.put("titulo","Amnares");
        //contentValues3.put("horario", "22:45");
        conexao.insertOrThrow("remedios", null, contentValues3);
    }

    public ArrayAdapter<String> buscaRemedios(Context context){
        ArrayAdapter<String> arrayAdapterRemdios = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = conexao.query("remedios", null, null, null, null, null, null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                String titulo = cursor.getString(1);
               // String horario = cursor.getString(2);
                arrayAdapterRemdios.add(titulo);//+" - "+horario);
            }while (cursor.moveToNext());
        }
        return arrayAdapterRemdios;
    }
}
