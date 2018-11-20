package ms.zem.agendae.remote;

import java.util.List;

import ms.zem.agendae.modelo.Especialidade;
import retrofit2.Call;
import retrofit2.http.GET;

public interface EspecialidadeService {

    @GET("especialidade/")
    Call<List<Especialidade>> getEspecialidades();

}
