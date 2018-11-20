package ms.zem.agendae.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public class Disponibilidade {

    @SerializedName("id")
    private Integer id;
    @SerializedName("especialidade")
    private Integer especialidade;
    @SerializedName("usuario")
    private Integer medico;
    @SerializedName("data")
    private Date data;
    @SerializedName("periodo")
    private String periodo;

    public Disponibilidade() {    }

    public Disponibilidade(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Integer especialidade) {
        this.especialidade = especialidade;
    }

    public Integer getMedico() {
        return medico;
    }

    public void setMedico(Integer medico) {
        this.medico = medico;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disponibilidade that = (Disponibilidade) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Disponibilidade{" +
                "id=" + id +
                ", especialidade=" + especialidade +
                ", medico=" + medico +
                ", data=" + data +
                ", periodo='" + periodo + '\'' +
                '}';
    }
}
