package ms.zem.agendae.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ms.zem.agendae.R;
import ms.zem.agendae.ui.consulta.ConsultaListActivity;
import ms.zem.agendae.ui.login.LoginActivity;
import ms.zem.agendae.util.Preferencia;

public class MainActivity extends BaseActivity {

    private static final long SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                testarLogin();
            }
        }, SPLASH_TIME_OUT);
    }

    public void testarLogin(){
        if (getPreferencia().usuarioLogado()){
            startActivity(new Intent(MainActivity.this, ConsultaListActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        finish();
    }
}
