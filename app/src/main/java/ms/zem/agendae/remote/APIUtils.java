package ms.zem.agendae.remote;

public class APIUtils {

    public APIUtils() {
    }

    public static final String API_URL = "http://192.168.88.252:8080/";

    public static UsuarioService getUsuarioService(){
        return RetrofitClient.getClient(API_URL).create(UsuarioService.class);
    }

    public static ConsultaService getConsultaService(){
        return RetrofitClient.getClient(API_URL).create(ConsultaService.class);
    }

    public static EspecialidadeService getEspecialidadeService(){
        return RetrofitClient.getClient(API_URL).create(EspecialidadeService.class);
    }

    public static DisponibilidadeService getDisponibilidadeService(){
        return RetrofitClient.getClient(API_URL).create(DisponibilidadeService.class);
    }

}
