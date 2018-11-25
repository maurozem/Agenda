package ms.zem.agendae.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ms.zem.agendae.util.Preferencia;

@SuppressLint("Registered")
abstract public class BaseActivity extends AppCompatActivity {

    private Preferencia preferencia;

    protected Preferencia getPreferencia() {
        if(preferencia == null){
            preferencia = new Preferencia(getApplicationContext());
        }
        return preferencia;
    }

    protected void toast(String mensagem){
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT).show();
    }
}
