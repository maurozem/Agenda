package ms.zem.agendae.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public class Consulta {

    public final static int[] MANHA = { 8, 9, 10 };
    public final static int[] TARDE = { 14, 15, 16, 17 };
    public final static int[] NOITE = { 18, 19, 20 };
    public final static int[] MINUTOS = { 0, 15, 30, 45 };


    @SerializedName("id")
    private Integer id;
    @SerializedName("disponibilidade")
    private Integer disponibilidade;
    @SerializedName("hora")
    private Integer hora;
    @SerializedName("minuto")
    private Integer minuto;
    @SerializedName("usuario")
    private Integer usuario;
    @SerializedName("confirmado")
    private Integer confirmado;

    @SerializedName("data")
    private Date data;
    @SerializedName("periodo")
    private String periodo;
    @SerializedName("paciente")
    private String paciente;
    @SerializedName("medico")
    private String medico;
    @SerializedName("especialidade")
    private String especialidade;


    public Consulta() {    }

    public Consulta(Integer id) {
        this.id = id;
    }

    public Consulta(Integer disponibilidade, Integer hora, Integer minuto, Integer paciente) {
        this.disponibilidade = disponibilidade;
        this.hora = hora;
        this.minuto = minuto;
        this.usuario = paciente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(Integer disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public Integer getMinuto() {
        return minuto;
    }

    public void setMinuto(Integer minuto) {
        this.minuto = minuto;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public Integer getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(Integer confirmado) {
        this.confirmado = confirmado;
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

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consulta consulta = (Consulta) o;
        return Objects.equals(id, consulta.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", disponibilidade=" + disponibilidade +
                ", hora=" + hora +
                ", minuto=" + minuto +
                ", usuario=" + usuario +
                ", confirmado=" + confirmado +
                ", data=" + data +
                ", periodo='" + periodo + '\'' +
                ", paciente='" + paciente + '\'' +
                ", medico='" + medico + '\'' +
                ", especialidade='" + especialidade + '\'' +
                '}';
    }

    public boolean isConfirmado() {
        return confirmado == 1;
    }

    public void setConfirmar(){
        confirmado = 1;
    }
}
