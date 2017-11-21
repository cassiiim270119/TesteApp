package com.example.cassianomoura.testeapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cassiano Moura on 26/09/2017.
 */

public class Banco extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "banco.db";
    private static final String TABELAREMEDIOS = "remedios";
    private static final String TABELAATIVIDADES = "atividades";
    private static final String TABELAEXERCICIOS = "exercicios";
    private static final String ID = "id";
    private static final String TITULO = "titulo";
    private static final String HORARIO = "horario";
    private static final int VERSAO = 1;

    public Banco(Context context){
        super(context, NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlremedios = "CREATE TABLE IF NOT EXISTS "+TABELAREMEDIOS+" ( "
                + ID + " integer primary key autoincrement, "
                + TITULO + " VARCHAR(100), "
                + HORARIO + " time "
                +" );";
        db.execSQL(sqlremedios);
       String sqlcompromissos = "CREATE TABLE IF NOT EXISTS "+TABELAATIVIDADES+" ( "
                + ID + " integer primary key, "
                + TITULO + " VARCHAR(100), "
                + HORARIO + " time "
                +" );";
        db.execSQL(sqlcompromissos);
        String sqlexercicios = "CREATE TABLE IF NOT EXISTS "+TABELAEXERCICIOS+" ( "
                + ID + " integer primary key, "
                + TITULO + " VARCHAR(100), "
                + HORARIO + " time "
                +" );";
        db.execSQL(sqlexercicios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABELAREMEDIOS);
        db.execSQL("DROP TABLE IF EXISTS" + TABELAATIVIDADES);
        db.execSQL("DROP TABLE IF EXISTS" + TABELAEXERCICIOS);
        onCreate(db);
    }
}
