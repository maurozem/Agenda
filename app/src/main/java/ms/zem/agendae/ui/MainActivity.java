package ms.zem.agendae.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ms.zem.agendae.R;
import ms.zem.agendae.util.Preferencia;

public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 2000;
    private Preferencia preferencia;

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
        preferencia = new Preferencia(getApplicationContext());
        if (preferencia.usuarioLogado()){
            startActivity(new Intent(MainActivity.this, ConsultaListActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        finish();
    }
}
