package ms.zem.agendae.dao;

import android.util.Log;

import java.util.List;

import ms.zem.agendae.modelo.Especialidade;
import ms.zem.agendae.remote.APIUtils;
import ms.zem.agendae.remote.EspecialidadeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EspecialidadeDAO {

    private final String TAG = "EspecialidadeDAO";

    private EspecialidadeService especialidadeService = APIUtils.getEspecialidadeService();

    public void getEspecialidade(final Executar executar) {

        Call<List<Especialidade>> call = especialidadeService.getEspecialidades();
        call.enqueue(new Callback<List<Especialidade>>() {
            @Override
            public void onResponse(Call<List<Especialidade>> call, Response<List<Especialidade>> response) {
                if (response.isSuccessful()) {
                    if (!response.body().isEmpty()) {
                        if(executar != null){
                            executar.sucesso(response.body());
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Especialidade>> call, Throwable t) {
                Log.e(TAG, "ERROR: " + t.getMessage());
            }
        });
    }

}
