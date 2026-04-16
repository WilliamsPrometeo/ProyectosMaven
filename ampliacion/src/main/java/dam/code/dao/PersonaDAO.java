package dam.code.dao;

import dam.code.exceptions.PersonaException;
import dam.code.model.Persona;

import java.sql.SQLException;
import java.util.List;

public interface PersonaDAO {

    void guardar(Persona persona);
    List<Persona> cargar();
}
