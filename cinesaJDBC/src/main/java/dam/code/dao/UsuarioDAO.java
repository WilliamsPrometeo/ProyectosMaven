package dam.code.dao;

import dam.code.exceptions.UsuarioException;
import dam.code.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {
    void registrar(Usuario usuario, String password) throws UsuarioException;
    Usuario login(String dni, String password) throws UsuarioException;
}
