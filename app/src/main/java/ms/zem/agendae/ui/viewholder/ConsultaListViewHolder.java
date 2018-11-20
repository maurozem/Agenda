package ms.zem.agendae.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ms.zem.agendae.R;

public class ConsultaListViewHolder extends RecyclerView.ViewHolder
        implements View.OnLongClickListener {


    public TextView tvData, tvHora, tvMinuto, tvConfirmado;
    public TextView tvEspecialidade, tvMedico, tvPaciente;

    private OnClick onClick;

    public ConsultaListViewHolder(View itemView) {
        super(itemView);

        itemView.setOnLongClickListener(this);

        tvData = itemView.findViewById(R.id.tvData);
        tvHora = itemView.findViewById(R.id.tvHora);
        tvMinuto = itemView.findViewById(R.id.tvMinuto);
        tvConfirmado = itemView.findViewById(R.id.tvConfirmado);
        tvEspecialidade = itemView.findViewById(R.id.tvEspecialidade);
        tvMedico = itemView.findViewById(R.id.tvMedico);
        tvPaciente = itemView.findViewById(R.id.tvPaciente);

    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    @Override
    public boolean onLongClick(View view) {
        if(onClick != null){
            onClick.onItemLongClick(view, getLayoutPosition());
        }
        return false;
    }
}