package dam.code.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dam.code.dto.PeliculaDTO;
import dam.code.repository.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonManager implements Repository<PeliculaDTO, Integer> {

    private final File file;
    private final Gson gson;

    public JsonManager(String dni) {
        File carpeta = new File("data/visualizaciones");

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        this.file = new File(carpeta, dni + ".json");
        gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }

    @Override
    public Map<PeliculaDTO, Integer> cargar() {
        try (FileReader reader = new FileReader(file)) {

            Type type = new TypeToken<Map<PeliculaDTO, Integer>>(){}.getType();
            Map<PeliculaDTO, Integer> datos = gson.fromJson(reader, type);
            return datos != null ? datos : new LinkedHashMap<>();

        } catch (IOException e) {
            return new LinkedHashMap<>();
        }
    }

    @Override
    public void guardar(Map<PeliculaDTO, Integer> visualizaciones) {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(visualizaciones, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
