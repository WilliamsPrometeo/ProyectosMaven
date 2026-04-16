package dam.code.dao.impl;

import dam.code.config.DatabaseConfig;
import dam.code.dao.PersonaDAO;
import dam.code.exceptions.PersonaException;
import dam.code.model.Persona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAOImpl implements PersonaDAO {

    @Override
    public void guardar(Persona persona) {
        String sql = "INSERT INTO personas (nombre, apellido) VALUES (?, ?)";

        try (Connection con = DatabaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellido());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al insertar persona: " + ex.getMessage());
        }
    }

    @Override
    public List<Persona> cargar() {
        List<Persona> personas = new ArrayList<>();
        String sql = "SELECT * FROM personas";

        try (Connection con = DatabaseConfig.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Persona p = new Persona(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                );
                personas.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar personas: " + e.getMessage());
        }

        return personas;
    }
}
