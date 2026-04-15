package dam.code.service;

import dam.code.dao.UsuarioDAO;
import dam.code.dao.impl.UsuarioDAOImpl;
import dam.code.exceptions.UsuarioException;
import dam.code.models.Usuario;

public class UsuarioService {
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    public Usuario login(String dni, String password) throws UsuarioException{
        return usuarioDAO.login(dni, password);
    }
    public void registrar(Usuario usuario, String password) throws UsuarioException {
        validarPassword(password);
        validarEmail(usuario.getEmail());
        usuarioDAO.registrar(usuario, password);
    }

    private void validarEmail(String email) throws UsuarioException{
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(regex)) {
            throw new UsuarioException("El formato del email no es válido");
        }

    }

    private void validarPassword(String password) throws UsuarioException {
        if (password.length() < 6) {
            throw new UsuarioException("La contraseña tiene que ser minimo de 6 caracteres");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new UsuarioException("La contraseña debe contener al menos una minúscula");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new UsuarioException("La contraseña debe contener al menos una mayúscula");
        }
        if (!password.matches(".*[@$!%*?&].*")) {
            throw new UsuarioException("La contraseña debe contener al menos uno de estos simbolos [@$!%*?&]");
        }
        if (!password.matches(".*\\d.*")) {
            throw new UsuarioException("La contraseña debe contener al menos un número");
        }
    }

}
