package ms.zem.agendae.remote;

import java.util.List;

import ms.zem.agendae.modelo.Consulta;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ConsultaService {

    @GET("consulta/{tipo}/{id}")
    Call<List<Consulta>> getConsultas(@Path("tipo") String tipo, @Path("id") int id);

    @GET("consulta/{disponibilidade}")
    Call<List<Consulta>> getConsultas(@Path("disponibilidade") int disponibilidade);

    @POST("consulta/")
    Call<Consulta> addConsulta(@Body Consulta consulta);

    @PUT("consulta/{id}")
    Call<Consulta> updateConsulta(@Path("id") int id, @Body Consulta consulta);

    @DELETE("consulta/{id}")
    Call<Consulta> deleteConsulta(@Path("id") int id);

}
