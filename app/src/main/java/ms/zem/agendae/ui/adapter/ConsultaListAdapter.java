package ms.zem.agendae.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ms.zem.agendae.R;
import ms.zem.agendae.dao.DAO;
import ms.zem.agendae.dao.Executar;
import ms.zem.agendae.modelo.Consulta;
import ms.zem.agendae.ui.viewholder.ConsultaListViewHolder;
import ms.zem.agendae.ui.viewholder.OnClick;
import ms.zem.agendae.util.Data;
import ms.zem.agendae.util.Dialog;
import ms.zem.agendae.util.Hora;

public class ConsultaListAdapter extends RecyclerView.Adapter<ConsultaListViewHolder> {

    private final int MILI = 100;

    private DAO dao;
    private List<Consulta> list;
    private Context context;

    public ConsultaListAdapter(List<Consulta> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ConsultaListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        context = viewGroup.getContext();
        return new ConsultaListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ConsultaListViewHolder viewHolder, int i) {

        final Consulta consulta = list.get(i);
        viewHolder.tvData.setText(Data.dataFormatada(consulta.getData()) );
        viewHolder.tvHora.setText( Hora.formatar(consulta.getHora()) );
        viewHolder.tvMinuto.setText( Hora.formatar(consulta.getMinuto()) );
        viewHolder.tvConfirmado.setText( consulta.getConfirmado() == 1 ? "Confirmado" : "" );
        viewHolder.tvMedico.setText( consulta.getMedico() );
        viewHolder.tvPaciente.setText( consulta.getPaciente() );
        viewHolder.tvEspecialidade.setText( consulta.getEspecialidade() );
        viewHolder.setOnClick(new OnClick() {
            @Override
            public void onItemLongClick(View v, int pos) {
                menu(pos);
            }
        });
    }

    private void menu(final int pos) {
        vibrar();
        if(!list.get(pos).isConfirmado()) {
            final String[] itens = new String[]{"Alterar", "Confirmar", "Desmarcar"};
            new Dialog(context).selecionarOpcao("Menu", itens, new Dialog.Executar() {
                @Override
                public void selecionado(int i) {
                    executarMenu(itens[i], pos);
                }
            });
        }
    }

    private void executarMenu(String item, int pos) {
        vibrar();
        dao = DAO.getInstance();
        Consulta consulta = list.get(pos);
        switch (item){
            case "Confirmar":
                consulta.setConfirmar();
                dao.getConsultaDAO().atualizar(consulta, new Executar() {
                    @Override
                    public void sucesso(Object object) {
                        notifyDataSetChanged();
                    }
                });
                break;
            case "Desmarcar":
                confirmaDesmarcar(consulta);
                break;
            case "Alterar":
                Toast.makeText(context, "Funcionalidade não implementada", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void confirmaDesmarcar(final Consulta consulta) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog);
        builder.setMessage("Confirma desmarcar consulta?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dao.getConsultaDAO().excluir(consulta, new Executar() {
                    @Override
                    public void sucesso(Object object) {
                        list.remove(consulta);
                        notifyDataSetChanged();
                    }
                });
            }
        });
        builder.setNegativeButton("Não", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void vibrar(){
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(MILI, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(MILI);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
