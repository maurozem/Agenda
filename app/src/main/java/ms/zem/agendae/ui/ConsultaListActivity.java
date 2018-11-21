package ms.zem.agendae.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ms.zem.agendae.R;
import ms.zem.agendae.dao.DAO;
import ms.zem.agendae.dao.Executar;
import ms.zem.agendae.modelo.Consulta;
import ms.zem.agendae.ui.adapter.ConsultaListAdapter;
import ms.zem.agendae.util.Preferencia;

public class ConsultaListActivity extends AppCompatActivity {

    private final int INCLUIR = 1;

    private DAO dao;
    private RecyclerView recyclerView;
    private ConsultaListAdapter adapter;
    private List<Consulta> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Consultas");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ConsultaListActivity.this, ConsultaActivity.class);
                startActivityForResult(it, INCLUIR);
            }
        });

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ConsultaListAdapter(list);
        recyclerView.setAdapter(adapter);

        dao = DAO.getInstance();
        Preferencia preferencia = new Preferencia(getApplicationContext());
        if(preferencia.usuarioLogado()){
            buscarConsultas();
        }
    }

    private void buscarConsultas() {

        dao.getConsultaDAO().getConsultas(new Executar() {
            @Override
            public void sucesso(Object object) {
                atualizarConsultas((List<Consulta>) object);
            }
        });
    }

    private void atualizarConsultas(List<Consulta> consultas) {
        list.clear();
        if(consultas != null && !consultas.isEmpty()) {
            list.addAll(consultas);
        } else {
            Toast.makeText(this, "Nenhum registro encontrado", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.sair:
                confirmaSair();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmaSair(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setMessage("Confirma log off?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Preferencia preferencia = new Preferencia(getApplicationContext());
                preferencia.setUsuario(null);
                finish();
            }
        });
        builder.setNegativeButton("Não", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == INCLUIR && resultCode == RESULT_OK){
            buscarConsultas();
        }
    }
}
