package com.example.taskmanager.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, NOME_BANCO_DADOS, null, VERSAO_BANCO_DADOS
) {

    companion object {
        const val VERSAO_BANCO_DADOS = 2
        const val NOME_BANCO_DADOS = "tarefas.db"
        const val NOME_TABELA_TAREFAS = "tarefas"
        const val ID_TAREFA = "id_tarefa"
        const val DESCRICAO = "descricao"
        const val DATA_CRIACAO = "data_criacao"
        const val ATIVO = "ativo"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val sqlTabela = "CREATE TABLE IF NOT EXISTS $NOME_TABELA_TAREFAS (" +
                "$ID_TAREFA INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "$DESCRICAO VARCHAR(70) NOT NULL," +
                "$DATA_CRIACAO DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");"
        try {
            db?.execSQL( sqlTabela )
            Log.i("info_db", "Sucesso ao criar a tabela v: $VERSAO_BANCO_DADOS")
        } catch (e: Exception){
            Log.i("info_db", "erro ao criar tabela ${e.message}")
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}