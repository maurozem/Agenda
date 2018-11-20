package ms.zem.agendae.remote;

import java.util.List;

import ms.zem.agendae.modelo.Disponibilidade;
import ms.zem.agendae.modelo.Especialidade;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DisponibilidadeService {

    @GET("disponibilidade/{especialidade}")
    Call<List<Disponibilidade>> getDisponibilidades(@Path("especialidade") int especialidade);

}
