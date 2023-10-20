package com.example.taskmanager.database

import com.example.taskmanager.model.Tarefa


interface ITarefaDAO {
    fun salvar( tarefa: Tarefa ): Boolean
    fun atualizar( tarefa: Tarefa): Boolean
    fun deletar( id: Int ): Boolean
    fun listar(): List<Tarefa>
}