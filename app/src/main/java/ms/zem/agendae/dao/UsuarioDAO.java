package ms.zem.agendae.dao;

import android.util.Log;

import java.util.List;

import ms.zem.agendae.modelo.Usuario;
import ms.zem.agendae.remote.APIUtils;
import ms.zem.agendae.remote.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioDAO {

    private final String TAG = "UsuarioDAO";

    private UsuarioService usuarioService = APIUtils.getUsuarioService();

    public void getUsuarioTipo(String tipo, final Executar executar) {

        Call<List<Usuario>> call = usuarioService.getUsuarioTipo(tipo);
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    if (!response.body().isEmpty()) {
                        if(executar != null){
                            executar.sucesso(response.body());
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e(TAG, "ERROR: " + t.getMessage());
            }
        });
    }

    public void getUsuario(String usuario, final Executar executar) {

        Call<List<Usuario>> call = usuarioService.getUsuario(usuario);
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    if (!response.body().isEmpty()) {
                        if(executar != null){
                            executar.sucesso(response.body().get(0));
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e(TAG, "ERROR: " + t.getMessage());
            }
        });
    }


    public void setToken(Usuario usuario, final Executar executar) {

        Call<Usuario> call = usuarioService.atualizarToken(usuario.getId(), usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    if(executar != null){
                        executar.sucesso(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e(TAG, "ERROR: " + t.getMessage());
            }
        });
    }

}
