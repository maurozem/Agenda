package ms.zem.agendae.ui.login;

import ms.zem.agendae.dao.Executar;
import ms.zem.agendae.modelo.Usuario;
import ms.zem.agendae.ui.Presenter;
import ms.zem.agendae.util.Preferencia;

public class LoginPresenter extends Presenter implements LoginContrato.Presenter {

    private LoginContrato.View view;

    public LoginPresenter(LoginContrato.View view) {
        this.view = view;
    }

    public void logar(String usuario, final String senha) {
        getDao().getUsuarioDAO().getUsuario(usuario,
                new Executar() {
                    @Override
                    public void sucesso(Object object) {
                        validarUsuario((Usuario) object, senha);
                    }
                }
        );
    }

    private void validarUsuario(Usuario usuario, String senha) {
        if (senha.equals(usuario.getSenha())){
            cadastrarUsuario(usuario);
        } else {
            view.senhaInvalida();
        }
    }

    private void cadastrarUsuario(Usuario usuario) {
        preferencia = new Preferencia(view.getContext());
        preferencia.setUsuario(usuario);
        view.IniciaConsultaLista();
    }

}
