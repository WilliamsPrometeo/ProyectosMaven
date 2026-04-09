package dam.code.dao;

import dam.code.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {
    void save(Usuario usuario);
    List<Usuario> findAll();
    Optional<Usuario> findById(int id);
    void delete(int id);
}
