package dam.code.persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dam.code.dto.LibroDTO;
import dam.code.model.Libro;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonManager {

    private static final String FILE_PATH = "libros.json";
    private static final Gson GSON = new Gson();

    public static void guardar(List<Libro> libros) {
        List<LibroDTO> dtoList = libros.stream()
                .map(Libro::toDTO)
                .toList();

        try (Writer writer = new FileWriter(FILE_PATH)) {
            GSON.toJson(dtoList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Libro> cargar() {
        try (Reader reader = new FileReader(FILE_PATH)) {

            Type listType = new TypeToken<List<LibroDTO>>(){}.getType();

            List<LibroDTO> dtoList = GSON.fromJson(reader, listType);

            if (dtoList == null) return new ArrayList<>();

            return dtoList.stream()
                    .map(Libro::fromDTO)
                    .toList();

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
