package dam.code.dao.impl;

import dam.code.dao.UsuarioDAO;
import dam.code.models.Usuario;

import java.util.List;
import java.util.Optional;

public class UsuarioDAOImpl implements UsuarioDAO {
    @Override
    public void save(Usuario usuario) {

    }

    @Override
    public List<Usuario> findAll() {
        return List.of();
    }

    @Override
    public Optional<Usuario> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void delete(int id) {

    }
}
