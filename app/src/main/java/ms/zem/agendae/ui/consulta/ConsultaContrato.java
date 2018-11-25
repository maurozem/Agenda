package ms.zem.agendae.ui.consulta;

import java.util.List;

import ms.zem.agendae.modelo.Consulta;
import ms.zem.agendae.modelo.Disponibilidade;
import ms.zem.agendae.modelo.Especialidade;
import ms.zem.agendae.modelo.Usuario;

public interface ConsultaContrato {

    interface Presenter {

        void buscarPacientes();
        void buscarHoras(Disponibilidade disponibilidade);
        void buscarDatas(Especialidade especialidade);
        void buscarEspecialidades();

        Boolean getExpertMode();
        void agendar(Consulta consulta);
    }

    interface View {

        void selecinarPaciente(List<Usuario> usuarios);
        void selecionarHoras(final List<String> horas, List<Consulta> consultas);
        void selecionarData(final List<Disponibilidade> disponibilidades);
        void selecionarEspecidade(List<Especialidade> especialidades);

        void picker();
        void agendamentoEfetuado();

    }
}
