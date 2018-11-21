package ms.zem.agendae.util;

import android.content.Context;
import android.content.SharedPreferences;

import ms.zem.agendae.modelo.Usuario;

public class Preferencia {

    private static Usuario usuario;
    private Context context;

    public Preferencia(Context context) {
        this.context = context;
    }

    public boolean usuarioLogado(){
        if(usuario == null){
            return getFromShared(context);
        } else {
            return true;
        }
    }

    public static Usuario getUsuario(){
        return usuario;
    }

    public void setUsuario(Usuario user){
        SharedPreferences pref =
                context.getSharedPreferences("UsuarioLogado", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        usuario = user;

        if(usuario == null){
            editor.clear();
            editor.apply();
        } else {
            editor.putInt("id", usuario.getId());
            editor.putString("usuario", usuario.getUsuario());
            editor.putString("nome", usuario.getNome());
            editor.putString("tipo", usuario.getTipo());
            editor.putString("token", usuario.getToken());
            editor.apply();
        }
    }

    private boolean getFromShared(Context context) {
        SharedPreferences pref =
                context.getSharedPreferences("UsuarioLogado", Context.MODE_PRIVATE);
        int id = pref.getInt("id", 0);
        if(id == 0){
            usuario = null;
            return false;
        } else {
            usuario = new Usuario(id);
            usuario.setUsuario(pref.getString("usuario", ""));
            usuario.setNome(pref.getString("nome", ""));
            usuario.setTipo(pref.getString("tipo", ""));
            usuario.setToken(pref.getString("token", ""));
            return true;
        }
    }

}
