package ms.zem.agendae.util;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;

public class Dialog {

    private final int MILI = 70;
    private Context context;
    private AlertDialog alertDialog;

    public Dialog(Context context) {
        this.context = context;
    }

    public void selecionarOpcao(String titulo, String[] itens, final Executar executar){
        vibrar();
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setItems(itens, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                vibrar();
                if(executar != null){
                    executar.selecionado(i);
                }
            }
        });
        alertDialog = builder.create();
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

    public interface Executar {
        void selecionado(int i);
    }

}
