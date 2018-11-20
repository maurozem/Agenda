package ms.zem.agendae.servico;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import ms.zem.agendae.dao.DAO;
import ms.zem.agendae.dao.Executar;
import ms.zem.agendae.modelo.Usuario;
import ms.zem.agendae.util.Preferencia;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        final Usuario usuario = Preferencia.getUsuario();
        if(usuario != null){
            if( usuario.getToken() == null || !token.equals(usuario.getToken()) ){
                usuario.setToken(token);
                DAO dao = DAO.getInstance();
                dao.getUsuarioDAO().setToken(usuario, new Executar() {
                    @Override
                    public void sucesso(Object object) {
                        Preferencia preferencia = new Preferencia(getApplicationContext());
                        preferencia.setUsuario(usuario);
                    }
                });
            }
        }
    }

}
