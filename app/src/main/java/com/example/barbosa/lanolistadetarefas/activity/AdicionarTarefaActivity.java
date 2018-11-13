package com.example.barbosa.lanolistadetarefas.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.barbosa.lanolistadetarefas.R;
import com.example.barbosa.lanolistadetarefas.helper.TarefaDAO;
import com.example.barbosa.lanolistadetarefas.model.Tarefa;

import java.util.zip.Inflater;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private Tarefa tarefaAtual;

    private TextInputEditText editTarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.textTarefa);

        //Recuperar tarefa, caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //configurar tarefa na caixa de texto
        if (tarefaAtual != null){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.itemAdicionar:
                //Executar ação para o item salvar, para projetos é indicado utilizar uma classe a parte

                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());


                if (tarefaAtual != null){//edição


                    String nomeTarefa = editTarefa.getText().toString();
                    if ( !nomeTarefa.isEmpty() ){

                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(tarefaAtual.getId());

                        //atualizar no bd

                        if (tarefaDAO.atualizar(tarefa)){

                            finish();
                            Toast.makeText(getApplicationContext(),"Sucesso ao atualizar a tarefa!", Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(getApplicationContext(),"Erro ao atualizar a tarefa!", Toast.LENGTH_LONG).show();

                        }
                    }


                }else {//salvar

                    String nomeTarefa = editTarefa.getText().toString();

                    if ( !nomeTarefa.isEmpty() ){

                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);

                        if (tarefaDAO.salvar(tarefa)){

                            finish();
                            Toast.makeText(getApplicationContext(),"Sucesso ao salvar tarefa!", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(getApplicationContext(),"Erro ao salvar tarefa!", Toast.LENGTH_LONG).show();
                        }



                    }


                }

                break;


        }
        return super.onOptionsItemSelected(item);
    }
}
