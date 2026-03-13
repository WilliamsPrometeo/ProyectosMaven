package dam.code.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dam.code.dto.LibroDTO;
import dam.code.model.Libro;
import dam.code.repository.LibroRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonManager implements LibroRepository {

    private final File file;
    private final Gson gson;

    public JsonManager(String path) {
        File directory = new File("data/editoriales");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        this.file = new File(directory, path + ".json");
        this.gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }

    @Override
    public Map<LibroDTO, String> cargar() {
        try (FileReader reader = new FileReader(file)) {

            Type type = new TypeToken<List<LibroDTO>>(){}.getType();
            Map<LibroDTO, String> map = gson.fromJson(reader, type);

            return map != null ? map : new LinkedHashMap<>();

        } catch (IOException e) {
            return new LinkedHashMap<>();
        }
    }

    @Override
    public void guardar(Map<LibroDTO, String> editoriales) {

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(editoriales, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
