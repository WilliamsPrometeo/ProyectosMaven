package dam.code.persistence;

import dam.code.models.Persona;
import dam.code.repository.Repository;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class RegistroDAO implements Repository<Persona, String> {

    private final File file;

    public RegistroDAO() {
        File carpeta = new File("data/registros");
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        this.file = new File(carpeta, "registros.dat");
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Persona, String> cargar() {

        if (!file.exists()) {
            return new LinkedHashMap<>();
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(file))) {

            return (Map<Persona, String>) ois.readObject();

        } catch (Exception e) {
            return new LinkedHashMap<>();
        }
    }

    @Override
    public void guardar(Map<Persona, String> registros) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(file))) {

            oos.writeObject(registros);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
