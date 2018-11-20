package ms.zem.agendae.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Data {

    public static String dataFormatada(Date data){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return sdf.format(data);
        } catch (Exception e){
            Log.e("Data(util)", e.getMessage());
            return null;
        }

    }

}
