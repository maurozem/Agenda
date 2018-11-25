package ms.zem.agendae.ui.login;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import ms.zem.agendae.R;
import ms.zem.agendae.dao.DAO;
import ms.zem.agendae.ui.BaseActivity;
import ms.zem.agendae.ui.consulta.ConsultaListActivity;

public class LoginActivity extends BaseActivity implements LoginContrato.View {

    private LoginContrato.Presenter presenter;

    private final String TAG = "LoginActivity";

    TextInputLayout tilUsuario;
    TextInputLayout tilSenha;
    AppCompatEditText edtUsuario;
    public AppCompatEditText edtSenha;
    AppCompatButton btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);

        setTitle("Logar na agenda");

        edtUsuario = findViewById(R.id.edtUsuario);
        edtSenha = findViewById(R.id.edtSenha);
        tilUsuario = findViewById(R.id.til_usuario);
        tilSenha = findViewById(R.id.til_senha);
        btnLogar = findViewById(R.id.btnLogar);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampos()) {
                    presenter.logar( edtUsuario.getText().toString(),
                            edtSenha.getText().toString() );
                }
            }
        });
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void senhaInvalida() {
        tilSenha.setErrorEnabled(true);
        tilSenha.setError("Senha inválida");
        edtSenha.requestFocus();
    }

    @Override
    public void IniciaConsultaLista() {
        startActivity(new Intent(LoginActivity.this, ConsultaListActivity.class));
        finish();
    }

    private boolean validarCampos() {
        if(edtUsuario.getText().toString().isEmpty()){
            tilUsuario.setErrorEnabled(true);
            tilUsuario.setError("Usuário deve ser preenchido");
            return false;
        } else {
            tilUsuario.setErrorEnabled(false);
            tilUsuario.setError("");
        }
        if(edtSenha.getText().toString().isEmpty()){
            tilSenha.setErrorEnabled(true);
            tilSenha.setError("Senha deve ser preenchida");
            return false;
        } else {
            tilSenha.setErrorEnabled(false);
            tilSenha.setError("");
        }
        return true;
    }
}
