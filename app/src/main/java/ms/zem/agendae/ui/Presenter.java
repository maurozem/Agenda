package ms.zem.agendae.ui;

import ms.zem.agendae.dao.DAO;
import ms.zem.agendae.util.Preferencia;

abstract public class Presenter {

    private DAO dao;
    protected Preferencia preferencia;

    public DAO getDao() {
        if(dao == null){
            dao = DAO.getInstance();
        }
        return dao;
    }

}
