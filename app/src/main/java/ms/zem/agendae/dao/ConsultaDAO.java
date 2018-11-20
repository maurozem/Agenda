package ms.zem.agendae.dao;

import android.util.Log;

import java.util.List;

import ms.zem.agendae.modelo.Consulta;
import ms.zem.agendae.remote.APIUtils;
import ms.zem.agendae.remote.ConsultaService;
import ms.zem.agendae.util.Preferencia;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultaDAO {

    private final String TAG = "ConsultaDAO";

    private ConsultaService consultaService = APIUtils.getConsultaService();


    public void excluir(Consulta consulta, final Executar executar) {

        Call<Consulta> call = consultaService.deleteConsulta(
                consulta.getId() );

        call.enqueue(new Callback<Consulta>() {
            @Override
            public void onResponse(Call<Consulta> call, Response<Consulta> response) {
                if (response.isSuccessful()) {
                    if(executar != null){
                        executar.sucesso(response.body());
                    }
                }
            }
            @Override
            public void onFailure(Call<Consulta> call, Throwable t) {
                Log.e(TAG, "ERROR: " + t.getMessage());
            }
        });
    }


    public void atualizar(Consulta consulta, final Executar executar) {

        Call<Consulta> call = consultaService.updateConsulta(
                consulta.getId(), consulta );

        call.enqueue(new Callback<Consulta>() {
            @Override
            public void onResponse(Call<Consulta> call, Response<Consulta> response) {
                if (response.isSuccessful()) {
                    if(executar != null){
                        executar.sucesso(response.body());
                    }
                }
            }
            @Override
            public void onFailure(Call<Consulta> call, Throwable t) {
                Log.e(TAG, "ERROR: " + t.getMessage());
            }
        });
    }


    public void incluir(Consulta consulta, final Executar executar) {

        Call<Consulta> call = consultaService.addConsulta( consulta );

        call.enqueue(new Callback<Consulta>() {
            @Override
            public void onResponse(Call<Consulta> call, Response<Consulta> response) {
                if (response.isSuccessful()) {
                    if(executar != null){
                        executar.sucesso(response.body());
                    }
                }
            }
            @Override
            public void onFailure(Call<Consulta> call, Throwable t) {
                Log.e(TAG, "ERROR: " + t.getMessage());
            }
        });
    }


    public void getConsultas(final Executar executar) {

        Call<List<Consulta>> call = consultaService.getConsultas(
                Preferencia.getUsuario().getTipo(),
                Preferencia.getUsuario().getId() );

        call.enqueue(new Callback<List<Consulta>>() {
            @Override
            public void onResponse(Call<List<Consulta>> call, Response<List<Consulta>> response) {
                if (response.isSuccessful()) {
                    if(executar != null){
                        executar.sucesso(response.body());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Consulta>> call, Throwable t) {
                Log.e(TAG, "ERROR: " + t.getMessage());
            }
        });
    }

    public void getConsultas(int disponibilidade, final Executar executar) {

        Call<List<Consulta>> call = consultaService.getConsultas( disponibilidade );

        call.enqueue(new Callback<List<Consulta>>() {
            @Override
            public void onResponse(Call<List<Consulta>> call, Response<List<Consulta>> response) {
                if (response.isSuccessful()) {
                    if(executar != null){
                        executar.sucesso(response.body());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Consulta>> call, Throwable t) {
                Log.e(TAG, "ERROR: " + t.getMessage());
            }
        });
    }
}
