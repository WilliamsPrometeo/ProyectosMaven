package dam.code.repository;

import dam.code.dto.LibroDTO;
import dam.code.model.Libro;

import java.util.List;
import java.util.Map;

public interface LibroRepository {

    Map<LibroDTO, String> cargar();

    void guardar(Map<LibroDTO, String> editoriales);
}
