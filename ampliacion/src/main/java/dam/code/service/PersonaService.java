package dam.code.service;

import dam.code.dao.PersonaDAO;
import dam.code.dao.impl.PersonaDAOImpl;
import dam.code.model.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonaService {

    private final PersonaDAO dao;

    public PersonaService() {
        dao = new PersonaDAOImpl();
    }

    public void registrar(Persona persona) {
        dao.guardar(persona);
    }

    public ObservableList<Persona> getPersonas() {
        return FXCollections.observableArrayList(dao.cargar());
    }
}
