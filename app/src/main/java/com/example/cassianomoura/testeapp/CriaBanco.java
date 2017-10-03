package com.example.cassianomoura.testeapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cassiano Moura on 26/09/2017.
 */

public class CriaBanco extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "banco.db";
    private static final String TABELAREMEDIOS = "remedios";
    private static final String TABELACOMPROMISSOS = "compromissos";
    private static final String TABELAEXERCICIOS = "exercicios";
    private static final int VERSAO = 1;

    public CriaBanco(Context context){
        super(context, NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlremedios = "CREATE TABLE "+TABELAREMEDIOS+" ("+
                "id      INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "titulo  TEXT    NOT NULL,"+
                "horario TIME    NOT NULL);";
        db.execSQL(sqlremedios);
        /*String sqlcompromissos = "CREATE TABLE "+TABELACOMPROMISSOS+" ("+
                "id      INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "titulo  TEXT    NOT NULL,"+
                "horario TIME    NOT NULL);";
        db.execSQL(sqlcompromissos);
        String sqlexercicios = "CREATE TABLE "+TABELAEXERCICIOS+" ("+
                "id      INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "titulo  TEXT    NOT NULL,"+
                "horario TIME    NOT NULL);";
        db.execSQL(sqlexercicios);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABELAREMEDIOS);
        //db.execSQL("DROP TABLE IF EXISTS" + TABELACOMPROMISSOS);
        //db.execSQL("DROP TABLE IF EXISTS" + TABELAEXERCICIOS);
        onCreate(db);
    }
}
