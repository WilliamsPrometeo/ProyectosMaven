package dam.code.service;

import dam.code.exception.PersonaException;
import dam.code.models.Persona;
import dam.code.repository.Repository;

import java.util.Map;

public class RegistroService {

    private final Map<Persona, String> registros;
    private final Repository<Persona, String> repository;

    public RegistroService(Repository<Persona, String> repository) {
        this.repository = repository;
        registros = repository.cargar();
    }

    public Map<Persona, String> getRegistros() {
        return Map.copyOf(registros);
    }

    public void registrarUsuario(Persona persona, String password) throws PersonaException {
        validarPassword(password);
        validarEmail(persona.getEmail());
        registros.put(persona, password);
        guardar();
    }

    public Persona comprobarCredenciales(String dni, String password) throws PersonaException {
        Persona p = getPersona(dni);
        if (p == null) {
            throw new PersonaException("Usuario no registrado");
        } else if (!registros.get(p).equals(password)) {
            throw new PersonaException("Credenciales incorrectas");
        }
        return p;
    }

    private Persona getPersona(String dni) {
        for (Persona p: registros.keySet()) {
            if (p.getDni().equals(dni)) return p;
        }
        return null;
    }

    private void validarPassword(String password) throws PersonaException {
        if (password.length() < 6) {
            throw new PersonaException("La contraseña tiene que ser mínimo de 6 caracteres");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new PersonaException("La contraseña debe contener al menos una minúscula");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new PersonaException("La contraseña debe contener al menos una mayúscula");
        }
        if (!password.matches(".*[@$!%*?&].*")) {
            throw new PersonaException("La contraseña debe contener al menos uno de estos simbolos [@$!%*?&]");
        }
        if (!password.matches(".*\\d.*")) {
            throw new PersonaException("La contraseña debe contener al menos un número");
        }
    }

    private void validarEmail(String email) throws PersonaException {

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (!email.matches(regex)) {
            throw new PersonaException("El formato del email no es válido");
        }

    }

    public void guardar() {
        repository.guardar(registros);
    }
}
