package ms.zem.agendae.ui.login;

import android.content.Context;

public interface LoginContrato {

    interface Presenter{

        void logar(String usuario, String senha);
    }

    interface View {

        void senhaInvalida();

        void IniciaConsultaLista();

        Context getContext();
    }

}
