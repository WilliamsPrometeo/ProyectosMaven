package dam.code.dao.impl;

import dam.code.dao.UsuarioDAO;
import dam.code.exceptions.UsuarioException;
import dam.code.models.Usuario;

import java.util.List;
import java.util.Optional;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public void registrar(Usuario usuario, String password) throws UsuarioException {

    }

    @Override
    public Usuario login(String dni, String password) throws UsuarioException {
        return null;
    }
}
