package ms.zem.agendae.ui.consulta;

import java.util.ArrayList;
import java.util.List;

import ms.zem.agendae.dao.Executar;
import ms.zem.agendae.modelo.Consulta;
import ms.zem.agendae.modelo.Disponibilidade;
import ms.zem.agendae.modelo.Especialidade;
import ms.zem.agendae.modelo.Usuario;
import ms.zem.agendae.ui.Presenter;
import ms.zem.agendae.util.Hora;
import ms.zem.agendae.util.Preferencia;

public class ConsultaPresenter extends Presenter implements ConsultaContrato.Presenter {

    private ConsultaContrato.View view;
    private Boolean expertMode;

    public ConsultaPresenter(ConsultaContrato.View view) {
        this.view = view;
    }

    public void buscarPacientes() {
        getDao().getUsuarioDAO().getUsuarioTipo("P",
                new Executar() {
                    @Override
                    public void sucesso(Object object) {
                        view.selecinarPaciente((List<Usuario>) object);
                    }
                });
    }

    public void buscarHoras(final Disponibilidade disponibilidade) {
        if(expertMode){
            view.picker();
        } else {
            getDao().getConsultaDAO().getConsultas(disponibilidade.getId(),
                    new Executar() {
                        @Override
                        public void sucesso(Object object) {
                            view.selecionarHoras(calcHoras(disponibilidade),
                                    (List<Consulta>) object);
                        }
                    });
        }
    }

    public void buscarDatas(Especialidade especialidade) {
        getDao().getDisponibilidadeDAO().getDisponibilidade(especialidade.getId(),
                new Executar() {
                    @Override
                    public void sucesso(Object object) {
                        view.selecionarData((List<Disponibilidade>) object);
                    }
                });
    }

    public void buscarEspecialidades() {
        getDao().getEspecialidadeDAO().getEspecialidade(new Executar() {
            @Override
            public void sucesso(Object object) {
                view.selecionarEspecidade((List<Especialidade>) object);
            }
        });
    }

    private List<String> calcHoras(Disponibilidade disponibilidade) {
        int[] periodo;
        if (disponibilidade.getPeriodo().equals("M")) {
            periodo = Consulta.MANHA;
        } else if (disponibilidade.getPeriodo().equals("T")) {
            periodo = Consulta.TARDE;
        } else {
            periodo = Consulta.NOITE;
        }
        List<String> horas = new ArrayList<>();
        for (int i = 0; i < periodo.length; i++) {
            for (int j = 0; j < Consulta.MINUTOS.length; j++) {
                horas.add( Hora.formatar(periodo[i], Consulta.MINUTOS[j]) );
            }
        }
        return horas;
    }

    public Boolean getExpertMode() {
        if(expertMode == null){
            expertMode = !Preferencia.getUsuario().getTipo().equals("P");
        }
        return expertMode;
    }

    @Override
    public void agendar(Consulta consulta) {
        getDao().getConsultaDAO().incluir(consulta, new Executar() {
            @Override
            public void sucesso(Object object) {
                view.agendamentoEfetuado();
            }
        });
    }
}
