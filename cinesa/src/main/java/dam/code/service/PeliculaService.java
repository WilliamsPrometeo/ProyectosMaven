package dam.code.service;

import dam.code.dto.PeliculaDTO;
import dam.code.exception.PeliculaException;
import dam.code.models.Pelicula;
import dam.code.repository.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PeliculaService {

    private final ObservableList<Pelicula> peliculas;
    private final Repository<PeliculaDTO, Integer> repository;
    private final Map<PeliculaDTO, Integer> visualizaciones;

    public PeliculaService(Repository<PeliculaDTO, Integer> repository) {
        this.repository = repository;
        visualizaciones = repository.cargar();
        peliculas = FXCollections.observableArrayList(cargar());
    }

    private List<Pelicula> cargar() {
        List<Pelicula> peliculaList = new ArrayList<>();
        if (!visualizaciones.isEmpty()) {
            for (Map.Entry<PeliculaDTO, Integer> map : visualizaciones.entrySet()) {
                peliculaList.add(Pelicula.fromDTO(map.getKey()));
            }
        }
        return peliculaList;
    }

    public ObservableList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void agregarPelicula(Pelicula pelicula) throws PeliculaException {
        validarPelicula(pelicula);
        peliculas.add(pelicula);
        visualizaciones.put(pelicula.toDTO(), 1);
        guardar();
    }

    public void eliminarPelicula(Pelicula pelicula) throws PeliculaException {
        if (pelicula == null) {
            throw new PeliculaException("Debe selecionar una pelicula");
        }
        peliculas.remove(pelicula);
        visualizaciones.remove(pelicula.toDTO());
        guardar();
    }

    public void actualizarTitulo(Pelicula pelicula, String nuevoTitulo) throws PeliculaException {
        if (nuevoTitulo.isEmpty()) {
            throw new PeliculaException("El título no puede estar vacio");
        }
        PeliculaDTO viejoDTO = pelicula.toDTO();

        Integer visual = visualizaciones.get(viejoDTO);

        pelicula.setTitulo(nuevoTitulo);

        visualizaciones.remove(viejoDTO);

        visualizaciones.put(pelicula.toDTO(), visual);
        guardar();
    }

    public void actualizarFecha(Pelicula pelicula, LocalDate nuevaFecha) throws PeliculaException {
        if (nuevaFecha.isAfter(LocalDate.now())) {
            throw new PeliculaException("La fecha de estreno no puede ser superior a la fecha actual");
        }
        PeliculaDTO viejoDTO = pelicula.toDTO();

        Integer visual = visualizaciones.get(viejoDTO);

        pelicula.setFechaPublicacion(nuevaFecha);

        visualizaciones.remove(viejoDTO);

        visualizaciones.put(pelicula.toDTO(), visual);
        guardar();
    }

    public void addVisualizacion(Pelicula pelicula){

        PeliculaDTO dto = pelicula.toDTO();

        visualizaciones.put(dto, visualizaciones.get(dto) + 1);

        guardar();
    }

    private void validarPelicula(Pelicula pelicula) throws PeliculaException {
        if (!pelicula.getId().matches("^[A-Z]{3}[0-9]{2}$")){
            throw new PeliculaException("El código debe tener formato: Letra + 2 números [ej: ABC01]");
        }
        if (pelicula.getTitulo() == null || pelicula.getTitulo().isBlank()) {
            throw new PeliculaException("El título es obligatorio");
        }
        if (pelicula.getDirector() == null || pelicula.getDirector().isBlank()) {
            throw new PeliculaException("El director es obligatorio");
        }
        if (pelicula.getDuracion() < 30 || pelicula.getDuracion() > 240) {
            throw new PeliculaException("La duración de la pelicula tiene que estar entre 30 y 240 minutos.");
        }
        if (pelicula.getFechaPublicacion().isAfter(LocalDate.now())) {
            throw new PeliculaException("La fecha de estreno no puede ser superior a la fecha actual");
        } else if (pelicula.getFechaPublicacion().isBefore(LocalDate.of(1800,1,1))){
            throw new PeliculaException("La fecha de estreno no puede ser inferior al 01/01/1800");
        }
    }

    private void guardar() {
        repository.guardar(visualizaciones);
    }
}
