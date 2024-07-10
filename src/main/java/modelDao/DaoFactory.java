package modelDao;

import db.DB;
import modelDaoImpl.UsuarioDaoJDBC;

public class DaoFactory {
    public static UsuarioDao createUsuarioDao(){
        return new UsuarioDaoJDBC(DB.gConnection());
    }
}
