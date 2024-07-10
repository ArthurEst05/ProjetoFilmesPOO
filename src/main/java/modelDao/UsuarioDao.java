package modelDao;

import java.util.List;

import entites.Usuario;

public interface UsuarioDao {
    void insert(Usuario obj);
    void update(Usuario obj);
    void deleteById(Integer id);
    Usuario findById(Integer id);
    List<Usuario> findAll();
}
