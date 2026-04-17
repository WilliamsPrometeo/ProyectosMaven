package dam.code.dao.impl;

import dam.code.config.DatabaseConfig;
import dam.code.dao.UsuarioDAO;
import dam.code.exceptions.UsuarioException;
import dam.code.models.Usuario;
import dam.code.models.utils.Rol;
import dam.code.utils.CryptPassword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public void registrar(Usuario usuario, String password) throws UsuarioException {
        String sql = "INSERT INTO usuarios (dni, nombre, email, password, rol) VALUES (?, ?, ?, ?, ?)";

        String hash = CryptPassword.hashPassword(password);

        try(Connection con = DatabaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getDni());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, hash);
            ps.setString(5, usuario.getRol().name());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new UsuarioException(e.getMessage());
        }
    }

    @Override
    public Usuario login(String dni, String password) throws UsuarioException {
        String sql = "SELECT * FROM usuarios WHERE dni = ?";

        try (Connection con = DatabaseConfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashBD = rs.getString("password");

                if (!CryptPassword.checkPassword(password, hashBD)) {
                    throw new UsuarioException("Usuario o contraseña incorrectos");
                }

                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        Rol.valueOf(rs.getString("rol"))
                );
            }

            throw new UsuarioException("Usuario o contraseña incorrectos");

        } catch (SQLException e) {
            throw new UsuarioException(e.getMessage());
        }

    }
}
