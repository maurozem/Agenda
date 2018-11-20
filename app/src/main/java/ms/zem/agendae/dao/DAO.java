package ms.zem.agendae.dao;

public class DAO {

    private static DAO dao;

    private UsuarioDAO usuarioDAO;
    private ConsultaDAO consultaDAO;
    private EspecialidadeDAO especialidadeDAO;
    private DisponibilidadeDAO disponibilidadeDAO;

    private DAO(){}

    public static DAO getInstance(){
        if (dao == null){
            dao = new DAO();
        }
        return dao;
    }

    public ConsultaDAO getConsultaDAO() {
        if(consultaDAO == null){
            consultaDAO = new ConsultaDAO();
        }
        return consultaDAO;
    }

    public DisponibilidadeDAO getDisponibilidadeDAO() {
        if(disponibilidadeDAO == null){
            disponibilidadeDAO = new DisponibilidadeDAO();
        }
        return disponibilidadeDAO;
    }

    public EspecialidadeDAO getEspecialidadeDAO() {
        if(especialidadeDAO == null){
            especialidadeDAO = new EspecialidadeDAO();
        }
        return especialidadeDAO;
    }

    public UsuarioDAO getUsuarioDAO() {
        if(usuarioDAO == null){
            usuarioDAO = new UsuarioDAO();
        }
        return usuarioDAO;
    }

}
