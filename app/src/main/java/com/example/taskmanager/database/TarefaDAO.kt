package com.example.taskmanager.database
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.taskmanager.model.Tarefa

class TarefaDAO(context: Context) : ITarefaDAO {

    private val escrita = DatabaseHelper(context).writableDatabase
    private val leitura = DatabaseHelper(context).readableDatabase

    override fun salvar(tarefa: Tarefa): Boolean {

        val conteudos = ContentValues()
        conteudos.put("${DatabaseHelper.DESCRICAO}",tarefa.descricao)

        try {
            escrita.insert(
                DatabaseHelper.NOME_TABELA_TAREFAS,
                null,
                conteudos
            )
            Log.i("info_db", "Sucesso ao salvar tarefa v: ${DatabaseHelper.VERSAO_BANCO_DADOS}")
        } catch (e: Exception){
            Log.i("info_db", "erro ao salvar tarefa ${e.message}")
            return false
        }

        return true
    }

    override fun atualizar(tarefa: Tarefa): Boolean {

        val args = arrayOf(tarefa.idtarefa.toString())
        val conteudo = ContentValues()
        conteudo.put("${DatabaseHelper.DESCRICAO}",tarefa.descricao)
        try {
            escrita.update(
                DatabaseHelper.NOME_TABELA_TAREFAS,
                conteudo,
                "${DatabaseHelper.ID_TAREFA} = ?",
                args

            )
            Log.i("info_db", "Sucesso ao atualizar a tarefa")
        } catch (e: Exception){
            e.printStackTrace()
            Log.i("info_db", "erro ao atualizar tarefa ${e.message}")
            return false
        }

        return true
    }

    override fun deletar(idTarefa: Int): Boolean {

        val args = arrayOf(idTarefa.toString())
        try {
            escrita.delete(
                DatabaseHelper.NOME_TABELA_TAREFAS,
                "${DatabaseHelper.ID_TAREFA} = ?",
                args

            )
            Log.i("info_db", "Sucesso ao remover a tabela")
        } catch (e: Exception){
            e.printStackTrace()
            Log.i("info_db", "erro ao remover tabela ${e.message}")
            return false
        }

        return true
    }

    override fun listar(): List<Tarefa> {
        val listaTarefas = mutableListOf<Tarefa>()

        val sql = "SELECT " +
                "${DatabaseHelper.ID_TAREFA}," +
                "${DatabaseHelper.DESCRICAO}," +
                "strftime('%d/%m/%Y %H:%M', ${DatabaseHelper.DATA_CRIACAO}) AS data_criacao " +
                "FROM ${DatabaseHelper.NOME_TABELA_TAREFAS}"


        val cursor = leitura.rawQuery(sql, null)

        val indiceId = cursor.getColumnIndex(DatabaseHelper.ID_TAREFA)
        val indiceDescricao = cursor.getColumnIndex(DatabaseHelper.DESCRICAO)
        val indiceData = cursor.getColumnIndex(DatabaseHelper.DATA_CRIACAO)

        while (cursor.moveToNext()){

            val idTarefa = cursor.getInt(indiceId)
            val descricao = cursor.getString(indiceDescricao)
            val data = cursor.getString(indiceData)

            listaTarefas.add(
                Tarefa(idTarefa, descricao, data)
            )


        }

        return listaTarefas
    }

}