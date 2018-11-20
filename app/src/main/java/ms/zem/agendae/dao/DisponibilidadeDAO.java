package ms.zem.agendae.dao;

import android.util.Log;

import java.util.List;

import ms.zem.agendae.modelo.Disponibilidade;
import ms.zem.agendae.remote.APIUtils;
import ms.zem.agendae.remote.DisponibilidadeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisponibilidadeDAO {

    private final String TAG = "DisponibilidadeDAO";

    private DisponibilidadeService disponibilidadeService = APIUtils.getDisponibilidadeService();

    public void getDisponibilidade(int especialidade, final Executar executar) {

        Call<List<Disponibilidade>> call = disponibilidadeService.getDisponibilidades(especialidade);
        call.enqueue(new Callback<List<Disponibilidade>>() {
            @Override
            public void onResponse(Call<List<Disponibilidade>> call, Response<List<Disponibilidade>> response) {
                if (response.isSuccessful()) {
                    if (!response.body().isEmpty()) {
                        if(executar != null){
                            executar.sucesso(response.body());
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Disponibilidade>> call, Throwable t) {
                Log.e(TAG, "ERROR: " + t.getMessage());
            }
        });
    }

}
