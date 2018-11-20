package ms.zem.agendae.ui;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import ms.zem.agendae.R;
import ms.zem.agendae.dao.DAO;
import ms.zem.agendae.dao.Executar;
import ms.zem.agendae.modelo.Usuario;
import ms.zem.agendae.util.Preferencia;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";

    private TextInputLayout tilUsuario;
    private TextInputLayout tilSenha;
    private AppCompatEditText edtUsuario;
    private AppCompatEditText edtSenha;
    private AppCompatButton btnLogar;

    private DAO dao;
    private Preferencia preferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Logar na agenda");
        dao = DAO.getInstance();

        edtUsuario = findViewById(R.id.edtUsuario);
        edtSenha = findViewById(R.id.edtSenha);
        tilUsuario = findViewById(R.id.til_usuario);
        tilSenha = findViewById(R.id.til_senha);
        btnLogar = findViewById(R.id.btnLogar);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampos()) {
                    logar();
                }
            }
        });
    }

    private void logar() {
        dao.getUsuarioDAO().getUsuario(edtUsuario.getText().toString(),
                new Executar() {
                    @Override
                    public void sucesso(Object object) {
                        validarUsuario((Usuario) object);
                    }
                }
        );
    }

    private void validarUsuario(Usuario usuario) {
        if (edtSenha.getText().toString().equals(usuario.getSenha())){
            cadastrarUsuario(usuario);
        } else {
            tilSenha.setErrorEnabled(true);
            tilSenha.setError("Senha inválida");
            edtSenha.requestFocus();
        }
    }

    private void cadastrarUsuario(Usuario usuario) {
        preferencia = new Preferencia(getApplicationContext());
        preferencia.setUsuario(usuario);
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
