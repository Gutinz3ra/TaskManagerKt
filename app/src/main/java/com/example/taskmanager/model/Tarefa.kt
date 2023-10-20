package com.example.taskmanager.model

import java.io.Serializable

data class Tarefa (
    val idtarefa : Int,
    val descricao: String,
    val dataCriacao: String
) : Serializable
