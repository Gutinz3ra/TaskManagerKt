package com.example.taskmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.adapter.TarefaAdapter
import com.example.taskmanager.database.TarefaDAO
import com.example.taskmanager.databinding.ActivityMainBinding
import com.example.taskmanager.model.Tarefa

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var listaTarefas = emptyList<Tarefa>()
    private var tarefaAdapter: TarefaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fabAdicionar.setOnClickListener {
            val intent = Intent(this, AdicionarTarefaActivity::class.java)
            startActivity(intent)
        }

        // Inicialize o adaptador corretamente
        tarefaAdapter = TarefaAdapter(
            { id -> confirmarExclusao(id) },
            { tarefa -> editar(tarefa) },

            )
        binding.rvTarefas.adapter = tarefaAdapter // Use o adaptador inicializado aqui
        binding.rvTarefas.layoutManager = LinearLayoutManager(this)
    }

    private fun editar(tarefa: Tarefa) {
        val intent = Intent(this, AdicionarTarefaActivity::class.java)
        intent.putExtra("tarefa", tarefa)
        startActivity(intent)

    }

    private fun confirmarExclusao(id: Int) {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("Confirmar exclusão")
        alertBuilder.setMessage("Deseja realmente excluir a tarefa?")

        alertBuilder.setPositiveButton("Sim") { _, _ ->
            val tarefaDAO = TarefaDAO(this)
            if (tarefaDAO.deletar(id)) {
                atualizarListaTarefas()
                Toast.makeText(this, "Sucesso ao remover Tarefa", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erro ao remover Tarefa", Toast.LENGTH_SHORT).show()
            }
        }

        alertBuilder.setNegativeButton("Não") { _, _ -> }

        alertBuilder.create().show()
    }

    private fun atualizarListaTarefas() {
        val tarefaDAO = TarefaDAO(this)
        listaTarefas = tarefaDAO.listar()
        tarefaAdapter?.adicionarLista(listaTarefas)
    }

    override fun onStart() {
        super.onStart()
        atualizarListaTarefas()
    }
}
