package ms.zem.agendae.modelo;

import java.util.Objects;

public class Tipo {

    private String tabela;
    private String campo;
    private String chave;
    private String valor;

    public Tipo() {
    }


    public Tipo(String tabela, String campo, String chave) {
        this.tabela = tabela;
        this.campo = campo;
        this.chave = chave;
    }

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tipo tipo = (Tipo) o;
        return Objects.equals(tabela, tipo.tabela) &&
                Objects.equals(campo, tipo.campo) &&
                Objects.equals(chave, tipo.chave);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tabela, campo, chave);
    }

    @Override
    public String toString() {
        return "Tipo{" +
                "tabela='" + tabela + '\'' +
                ", campo='" + campo + '\'' +
                ", chave='" + chave + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}
