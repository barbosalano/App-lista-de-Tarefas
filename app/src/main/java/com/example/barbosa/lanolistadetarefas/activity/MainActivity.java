package com.example.barbosa.lanolistadetarefas.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.barbosa.lanolistadetarefas.R;
import com.example.barbosa.lanolistadetarefas.adapter.TarefaAdapter;
import com.example.barbosa.lanolistadetarefas.helper.DBhelper;
import com.example.barbosa.lanolistadetarefas.helper.RecyclerItemClickListener;
import com.example.barbosa.lanolistadetarefas.helper.TarefaDAO;
import com.example.barbosa.lanolistadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerListaTarefas;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //configurar recicler
        recyclerListaTarefas = findViewById(R.id.recyclerListaTarefas);

        //instanciando o banco de dados
       /* ContentValues cv = new ContentValues();
        cv.put("nome", "Teste");

        DBhelper db = new DBhelper(getApplicationContext());
        db.getWritableDatabase().insert("tarefas", null, cv );*/


        //adicionar evento de clique
        recyclerListaTarefas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerListaTarefas,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                //recuperar tarefa para edição
                                Tarefa tarefaSelecionada = listaTarefas.get(position);


                                //Enviar para proxima tela
                                Intent intent = new Intent(MainActivity.this, AdicionarTarefaActivity.class);
                                intent.putExtra("tarefaSelecionada", tarefaSelecionada);

                                startActivity(intent);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                tarefaSelecionada = listaTarefas.get(position);
                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                                //configurar Titulo e mensagem
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir a tarefa: " + tarefaSelecionada.getNomeTarefa() + " ?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                                        if (tarefaDAO.deletar(tarefaSelecionada)) {

                                            carregarListaTarefas();

                                            Toast.makeText(getApplicationContext(), "Sucesso ao deletar a tarefa!", Toast.LENGTH_LONG).show();

                                        } else {

                                            Toast.makeText(getApplicationContext(), "Erro ao excluir tarefa!", Toast.LENGTH_LONG).show();
                                        }


                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                //Exibir
                                dialog.create();
                                dialog.show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                startActivity(intent);

            }
        });
    }

    public void carregarListaTarefas() {

        //Listar tarefas

        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        listaTarefas = tarefaDAO.listar();

        //Exibir lista de tarefas no Recyclerview


        //1 - configurar um adapter

        tarefaAdapter = new TarefaAdapter(listaTarefas);


        //configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerListaTarefas.setLayoutManager(layoutManager);
        recyclerListaTarefas.setHasFixedSize(true);
        recyclerListaTarefas.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerListaTarefas.setAdapter(tarefaAdapter);

    }

    @Override
    protected void onStart() {

        carregarListaTarefas();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.itemAdicionar) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
