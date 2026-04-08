package dam.code.service;

import dam.code.dao.UsuarioDAO;
import dam.code.dao.impl.UsuarioDAOImpl;
import dam.code.model.Usuario;

import java.util.List;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    public void crearUsuario(String nombre, String email) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        usuarioDAO.save(new Usuario(nombre, email));
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.findAll();
    }

    public void eliminarUsuario(int id) {
        usuarioDAO.delete(id);
    }
}
