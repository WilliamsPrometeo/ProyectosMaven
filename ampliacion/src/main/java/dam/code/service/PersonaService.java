package dam.code.service;

import dam.code.model.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class PersonaService {
    private ObservableList<Persona> personas = FXCollections.observableArrayList(new ArrayList<>());

    public void registrar(Persona persona) {
        personas.add(persona);
    }

    public ObservableList<Persona> getPersonas() {
        return personas;
    }
}
