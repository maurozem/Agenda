package ms.zem.agendae.util;

public class Hora {

    public static String formatar(Integer hora, Integer minuto) {
        return formatar(hora) + ":" + formatar(minuto);
    }

    public static String formatar(Integer hora) {
        if(hora != null){
            if(hora == 0){
                return "00";
            } else if(hora < 10){
                return "0" + String.valueOf(hora);
            } else {
                return String.valueOf(hora);
            }
        } else {
            return "00";
        }
    }
}
