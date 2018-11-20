package ms.zem.agendae.remote;

import java.util.List;

import ms.zem.agendae.modelo.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioService {

    @GET("usuario/{usuario}")
    Call<List<Usuario>> getUsuario(@Path("usuario") String usuario);

    @GET("usuarioTipo/{tipo}")
    Call<List<Usuario>> getUsuarioTipo(@Path("tipo") String tipo);

    @PUT("usuario/{id}")
    Call<Usuario> atualizarToken(@Path("id") int id, @Body Usuario usuario);

}
