package com.example.taskmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.taskmanager.database.TarefaDAO
import com.example.taskmanager.databinding.ActivityAdicionarTarefaBinding
import com.example.taskmanager.model.Tarefa

class AdicionarTarefaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAdicionarTarefaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //recuperar tarefa passada
        var tarefa: Tarefa? = null
        val bundle = intent.extras
        if(bundle != null){
            tarefa = bundle.getSerializable("tarefa") as Tarefa
            binding.editTarefa.setText(tarefa.descricao)
        }




        binding.btnSalvar.setOnClickListener {
            if (binding.editTarefa.text.isNotEmpty()){

                if (tarefa != null){
                    editar(tarefa)
                }else{
                    salvar()

                }



            }else{
                Toast.makeText(this, "Preencha uma tarefa", Toast.LENGTH_SHORT).show()
            }
         }
    }

    private fun editar(tarefa: Tarefa) {

        val descricao = binding.editTarefa.text.toString()
        val tarefaAtualizar = Tarefa(
            tarefa.idtarefa, descricao, "default"
        )
        val tarefaDAO = TarefaDAO(this)

        if (tarefaDAO.atualizar(tarefaAtualizar)){
            Toast.makeText(this, "Tarefa atualizada com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    private fun salvar() {
        val descricao = binding.editTarefa.text.toString()
        val tarefa = Tarefa(
            -1, descricao, "default"
        )

        val tarefaDAO = TarefaDAO(this)
        if (tarefaDAO.salvar(tarefa)) {
            Toast.makeText(this, "Tarefa cadastrada com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}