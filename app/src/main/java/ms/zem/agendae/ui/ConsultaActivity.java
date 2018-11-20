package ms.zem.agendae.ui;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ms.zem.agendae.R;
import ms.zem.agendae.dao.DAO;
import ms.zem.agendae.dao.Executar;
import ms.zem.agendae.modelo.Consulta;
import ms.zem.agendae.modelo.Disponibilidade;
import ms.zem.agendae.modelo.Especialidade;
import ms.zem.agendae.modelo.Usuario;
import ms.zem.agendae.util.Data;
import ms.zem.agendae.util.Dialog;
import ms.zem.agendae.util.Hora;
import ms.zem.agendae.util.Preferencia;

public class ConsultaActivity extends AppCompatActivity {

    private AppCompatEditText edtEspecialidade;
    private AppCompatEditText edtPaciente;
    private AppCompatEditText edtData;
    private AppCompatEditText edtHora;
    private AppCompatButton btnAgendar;
    private DAO dao;
    private Consulta consulta;
    private Especialidade especialidade;
    private Disponibilidade disponibilidade;

    private boolean expertMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        expertMode = !Preferencia.getUsuario().getTipo().equals("P");

        dao = DAO.getInstance();
        consulta = new Consulta();



        edtPaciente = findViewById(R.id.edtPaciente);
        edtPaciente.setKeyListener(null);
        edtEspecialidade = findViewById(R.id.edtEspecialidade);
        edtEspecialidade.setKeyListener(null);
        edtEspecialidade.setEnabled(false);
        edtData = findViewById(R.id.edtData);
        edtData.setKeyListener(null);
        edtData.setEnabled(false);
        edtHora = findViewById(R.id.edtHora);
        edtHora.setKeyListener(null);
        edtHora.setEnabled(false);
        btnAgendar = findViewById(R.id.btnAgendar);
        btnAgendar.setVisibility(View.GONE);

        if(!expertMode){
            consulta.setUsuario( Preferencia.getUsuario().getId() );
            edtPaciente.setVisibility(View.GONE);
            edtEspecialidade.setEnabled(true);
        }

        edtPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarPacientes();
            }
        });
        edtEspecialidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarEspecialidades();
            }
        });
        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarDatas();
            }
        });
        edtHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarHoras();
            }
        });
        btnAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ConsultaActivity.this, "kokokok", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscarPacientes() {
        dao.getUsuarioDAO().getUsuarioTipo(Preferencia.getUsuario().getTipo(),
                new Executar() {
                    @Override
                    public void sucesso(Object object) {
                        selecinarPaciente((List<Usuario>) object);
                    }
                });
    }

    private void selecinarPaciente(List<Usuario> pacientes) {
        String nomes[] = new String[pacientes.size()];
        for (int i = 0; i < pacientes.size(); i++) {
            nomes[i] = pacientes.get(i).getNome();
        }
    }

    private void buscarHoras() {
        if(expertMode){
            picker();
        } else {
            dao.getConsultaDAO().getConsultas(disponibilidade.getId(),
                    new Executar() {
                        @Override
                        public void sucesso(Object object) {
                            selecionarHoras(calcHoras(), (List<Consulta>) object);
                        }
                    });
        }
    }

    private void picker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ConsultaActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                edtHora.setText( selectedHour + ":" + selectedMinute);
                consulta.setHora( selectedHour );
                consulta.setMinuto( selectedMinute );
                btnAgendar.setVisibility(View.VISIBLE);
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Hora");
        mTimePicker.show();
    }

    private void selecionarHoras(List<String> horas, List<Consulta> consultas){
        List<String> dele = new ArrayList<>();
        for(Consulta c : consultas){
            dele.add( Hora.formatar(c.getHora(), c.getMinuto()) );
        }
        horas.removeAll(dele);
        new Dialog(ConsultaActivity.this)
                .selecionarOpcao("Hora", horas.toArray(),
                        new Dialog.Executar() {
                            @Override
                            public void selecionado(int i) {
                                edtHora.setText(horas.get(i));
                                consulta.setHora(Integer.getInteger(horas.get(i).split(":")[0]));
                                consulta.setMinuto(Integer.getInteger(horas.get(i).split(":")[1]));
                                btnAgendar.setVisibility(View.VISIBLE);
                            }
                        });
    }

    private List<String> calcHoras() {
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

    private void buscarDatas() {
        dao.getDisponibilidadeDAO().getDisponibilidade(especialidade.getId(),
                new Executar() {
                    @Override
                    public void sucesso(Object object) {
                        selecionarData((List<Disponibilidade>) object);
                    }
                });
    }

    private void selecionarData(final List<Disponibilidade> disponibilidades) {
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
                                edtHora.setText(null);
                                edtHora.setEnabled(true);
                                consulta.setHora(null);
                                consulta.setMinuto(null);
                                btnAgendar.setVisibility(View.GONE);
                                buscarHoras();
                            }
                        });
    }

    private void buscarEspecialidades() {
        dao.getEspecialidadeDAO().getEspecialidade(new Executar() {
            @Override
            public void sucesso(Object object) {
                selecionarEspecidade((List<Especialidade>) object);
            }
        });
    }

    private void selecionarEspecidade(final List<Especialidade> especialidades) {
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
                        edtData.setText(null);
                        edtData.setEnabled(true);
                        consulta.setDisponibilidade(null);
                        edtHora.setText(null);
                        edtHora.setEnabled(false);
                        consulta.setHora(null);
                        consulta.setMinuto(null);
                        btnAgendar.setVisibility(View.GONE);
                        buscarDatas();
                    }
                });
    }
}
