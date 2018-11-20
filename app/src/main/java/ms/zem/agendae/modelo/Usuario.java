package ms.zem.agendae.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Usuario {

    @SerializedName("id")
    private Integer id;
    @SerializedName("usuario")
    private String usuario;
    @SerializedName("senha")
    private String senha;
    @SerializedName("tipo")
    private String tipo;
    @SerializedName("nome")
    private String nome;
    @SerializedName("token")
    private String token;

    public Usuario() {    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", senha='" + senha + '\'' +
                ", tipo='" + tipo + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}
