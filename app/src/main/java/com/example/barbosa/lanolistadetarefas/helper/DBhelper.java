package com.example.barbosa.lanolistadetarefas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBhelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public   static  String NOME_DB = "DB_TAREFAS";
    public static  String TABELA_TAREFAS = "tarefas";

    public DBhelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = " CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome TEXT NOT NULL ); ";

        try {

            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a tabela" );


        }catch (Exception e){

            Log.i("INFO DB", "Erro ao criar tabela " + e.getMessage());

        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //PARTE QUE FAZ A ATUALIZAÇÃO DA VERSÃO DO APP
      String sql = "DROP TABLE IF EXISTS " + TABELA_TAREFAS + " ;";

        //PARA ALTERAR TABELA
       // String sql = " ALTER TABLE " + TABELA_TAREFAS + " ADD COLUMN status VARCHAR(2) ";

        try {

            db.execSQL( sql );
            onCreate(db);
            Log.i("INFO DB", "Sucesso ao atualizar app" );


        }catch (Exception e){

            Log.i("INFO DB", "Erro ao atualizar app " + e.getMessage());

        }

    }
}
