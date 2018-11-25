package ms.zem.agendae.ui.consulta;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ms.zem.agendae.R;
import ms.zem.agendae.modelo.Consulta;
import ms.zem.agendae.modelo.Disponibilidade;
import ms.zem.agendae.modelo.Especialidade;
import ms.zem.agendae.modelo.Usuario;
import ms.zem.agendae.ui.BaseActivity;
import ms.zem.agendae.util.Data;
import ms.zem.agendae.util.Dialog;
import ms.zem.agendae.util.Hora;
import ms.zem.agendae.util.Preferencia;

public class ConsultaActivity extends BaseActivity implements ConsultaContrato.View{

    private ConsultaContrato.Presenter presenter;

    private AppCompatEditText edtEspecialidade;
    private AppCompatEditText edtPaciente;
    private AppCompatEditText edtData;
    private AppCompatEditText edtHora;
    private AppCompatButton btnAgendar;
    private Consulta consulta;
    private Especialidade especialidade;
    private Disponibilidade disponibilidade;
    private Usuario paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        presenter = new ConsultaPresenter(this);
        consulta = new Consulta();

        edtPaciente = findViewById(R.id.edtPaciente);
        edtPaciente.setKeyListener(null);
        edtEspecialidade = findViewById(R.id.edtEspecialidade);
        edtEspecialidade.setKeyListener(null);
        edtData = findViewById(R.id.edtData);
        edtData.setKeyListener(null);
        edtHora = findViewById(R.id.edtHora);
        edtHora.setKeyListener(null);
        btnAgendar = findViewById(R.id.btnAgendar);

        edtPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.buscarPacientes();
            }
        });
        edtEspecialidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.buscarEspecialidades();
            }
        });
        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.buscarDatas(especialidade);
            }
        });
        edtHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.buscarHoras(disponibilidade);
            }
        });
        btnAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.agendar(consulta);

            }
        });

        if(presenter.getExpertMode()){
            edtPaciente.setVisibility(View.VISIBLE);
            escondeEspecialidade(false);
            presenter.buscarPacientes();
        } else {
            consulta.setUsuario( Preferencia.getUsuario().getId() );
            edtPaciente.setVisibility(View.GONE);
            escondeEspecialidade(true);
            presenter.buscarEspecialidades();
        }
    }

    public void agendamentoEfetuado() {
        toast("Agendamento efetuado");
        setResult(RESULT_OK);
        finish();
    }


    public void selecinarPaciente(final List<Usuario> pacientes) {
        String nomes[] = new String[pacientes.size()];
        for (int i = 0; i < pacientes.size(); i++) {
            nomes[i] = pacientes.get(i).getNome();
        }
        new Dialog(ConsultaActivity.this)
                .selecionarOpcao("Paciente", nomes,
                        new Dialog.Executar() {
                            @Override
                            public void selecionado(int i) {
                                paciente = pacientes.get(i);
                                edtPaciente.setText(paciente.getNome());
                                consulta.setUsuario(paciente.getId());
                                edtEspecialidade.setEnabled(true);
                                presenter.buscarEspecialidades();
                            }
                        });
    }

    public void picker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ConsultaActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                edtHora.setText( "" + selectedHour + ":" + selectedMinute);
                consulta.setHora( selectedHour );
                consulta.setMinuto( selectedMinute );
                btnAgendar.setVisibility(View.VISIBLE);
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Hora");
        mTimePicker.show();
    }

    public void selecionarHoras(final List<String> horas, List<Consulta> consultas){
        if(consultas != null && !consultas.isEmpty()) {
            List<String> dele = new ArrayList<>();
            for (Consulta c : consultas) {
                dele.add(Hora.formatar(c.getHora(), c.getMinuto()));
            }
            horas.removeAll(dele);
        }
        final String[] hhoras = new String[horas.size()];
        for (int i = 0; i < horas.size(); i++) {
            hhoras[i] = horas.get(i);
        }
        new Dialog(ConsultaActivity.this)
                .selecionarOpcao("Hora", hhoras,
                        new Dialog.Executar() {
                            @Override
                            public void selecionado(int i) {
                                edtHora.setText(horas.get(i));
                                consulta.setHora(Integer.valueOf(horas.get(i).split(":")[0]));
                                consulta.setMinuto(Integer.valueOf(horas.get(i).split(":")[1]));
                                btnAgendar.setVisibility(View.VISIBLE);
                            }
                        });
    }

    public void selecionarData(final List<Disponibilidade> disponibilidades) {
        if( disponibilidades != null && !disponibilidades.isEmpty()) {
            final String[] espec = new String[disponibilidades.size()];
            for (int i = 0; i < disponibilidades.size(); i++) {
                espec[i] = Data.dataFormatada(disponibilidades.get(i).getData());
            }
            new Dialog(ConsultaActivity.this)
                    .selecionarOpcao("Datas",
                            espec, new Dialog.Executar() {
                                @Override
                                public void selecionado(int i) {
                                    disponibilidade = disponibilidades.get(i);
                                    edtData.setText(espec[i]);
                                    consulta.setDisponibilidade(disponibilidade.getId());
                                    escondeHora(true);
                                    presenter.buscarHoras(disponibilidade);
                                }
                            });
        } else {
            toast("Nenhuma data disponível");
        }
    }

    public void selecionarEspecidade(final List<Especialidade> especialidades) {
        String[] espec = new String[especialidades.size()];
        for (int i = 0; i < especialidades.size(); i++) {
            espec[i] = especialidades.get(i).getNome();
        }
        new Dialog(ConsultaActivity.this)
                .selecionarOpcao("Especialidades",
                        espec, new Dialog.Executar() {
                            @Override
                            public void selecionado(int i) {
                                especialidade = especialidades.get(i);
                                edtEspecialidade.setText(especialidade.getNome());
                                escondeData(true);
                                presenter.buscarDatas(especialidade);
                            }
                        });
    }

    private void escondeEspecialidade(boolean ok) {
        edtEspecialidade.setText(null);
        edtEspecialidade.setEnabled(ok);
        consulta.setEspecialidade(null);
        especialidade = null;
        escondeData(false);
    }

    private void escondeData(boolean ok) {
        edtData.setText(null);
        edtData.setEnabled(ok);
        consulta.setDisponibilidade(null);
        disponibilidade = null;
        escondeHora(false);
    }

    private void escondeHora(boolean ok){
        edtHora.setText(null);
        edtHora.setEnabled(ok);
        consulta.setHora(null);
        consulta.setMinuto(null);
        btnAgendar.setVisibility(View.GONE);
    }
}
